package sbnz.integracija.chefadvisor;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.github.jhipster.config.DefaultProfileUtil;
import io.github.jhipster.config.JHipsterConstants;
import sbnz.integracija.chefadvisor.config.ApplicationProperties;
import sbnz.integracija.chefadvisor.facts.AlarmTriggerTemplateModel;
import sbnz.integracija.chefadvisor.facts.SpamDetectionTemplateModel;
import sbnz.integracija.chefadvisor.repository.AlarmTriggerTemplateRepository;
import sbnz.integracija.chefadvisor.repository.SpamDetectionTemplateRepository;

@SpringBootApplication
@EnableConfigurationProperties({LiquibaseProperties.class, ApplicationProperties.class})
@EntityScan(basePackages = {"sbnz.integracija.chefadvisor.domain"})
@EnableTransactionManagement
public class ChefadvisorApp {

    private static final Logger log = LoggerFactory.getLogger(ChefadvisorApp.class);

    private final Environment env;
    
    private AlarmTriggerTemplateRepository alarmTriggerTemplateRepository;
    
    private SpamDetectionTemplateRepository spamDetectionTemplateRepository;

    public ChefadvisorApp(Environment env, AlarmTriggerTemplateRepository alarmTriggerTemplateRepository, SpamDetectionTemplateRepository spamDetectionTemplateRepository) {
        this.env = env;
        this.alarmTriggerTemplateRepository = alarmTriggerTemplateRepository;
        this.spamDetectionTemplateRepository = spamDetectionTemplateRepository;
    }
    
    

    /**
     * Initializes chefadvisor.
     * <p>
     * Spring profiles can be configured with a program argument --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on <a href="https://www.jhipster.tech/profiles/">https://www.jhipster.tech/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " +
                "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)) {
            log.error("You have misconfigured your application! It should not " +
                "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ChefadvisorApp.class);
        DefaultProfileUtil.addDefaultProfile(app);
        ApplicationContext ctx = app.run(args);
        Environment env = ctx.getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\t{}://localhost:{}{}\n\t" +
                "External: \t{}://{}:{}{}\n\t" +
                "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            serverPort,
            contextPath,
            protocol,
            hostAddress,
            serverPort,
            contextPath,
            env.getActiveProfiles());
    }

    @Bean
    public KieContainer kieContainer() {
      KieServices ks = KieServices.Factory.get();
      KieContainer kContainer = ks
          .newKieContainer(ks.newReleaseId("sbnz.integracija", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
      KieScanner kScanner = ks.newKieScanner(kContainer);
      kScanner.start(10_000);
      return kContainer;
    }

    @Bean
    public KieSession cepKieSession() {
        ObjectDataCompiler converter = new ObjectDataCompiler();

    	InputStream spamDetectionTemplate = getClass().getResourceAsStream("/sbnz/integracija/spam-protection.drt");
        
//        List<SpamDetectionTemplateModel> spamDetectionData = new ArrayList<SpamDetectionTemplateModel>();
//        
//        spamDetectionData.add(new SpamDetectionTemplateModel(9, 6, "SPAMMING_COMMENTS"));
//        spamDetectionData.add(new SpamDetectionTemplateModel(2, 3, "SPAMMING_BAD_RATING"));
        
        String spamDetectionDRL = converter.compile(this.spamDetectionTemplateRepository.findAll(), spamDetectionTemplate);
        
        System.out.println(spamDetectionDRL);
        
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(spamDetectionDRL, ResourceType.DRL);

        InputStream alarmTriggerTemplate = getClass().getResourceAsStream("/sbnz/integracija/alarm.drt");
        
//        List<AlarmTriggerTemplateModel> alarmTemplateData = new ArrayList<AlarmTriggerTemplateModel>();
//        
//        alarmTemplateData.add(new AlarmTriggerTemplateModel(2));
        
        String alarmTriggerDRL = converter.compile(this.alarmTriggerTemplateRepository.findAll(), alarmTriggerTemplate);
        
        System.out.println(alarmTriggerDRL);

        kieHelper.addContent(alarmTriggerDRL, ResourceType.DRL);

        return kieHelper.build().newKieSession();
    }
  
}
