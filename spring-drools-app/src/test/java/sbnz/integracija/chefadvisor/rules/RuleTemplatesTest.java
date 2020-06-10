package sbnz.integracija.chefadvisor.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.drools.template.ObjectDataCompiler;
import org.junit.jupiter.api.Test;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;

import sbnz.integracija.chefadvisor.events.UserActionEvent;
import sbnz.integracija.chefadvisor.facts.AlarmTriggerTemplateModel;
import sbnz.integracija.chefadvisor.facts.SpamDetectionTemplateModel;

public class RuleTemplatesTest {
	
	@Test
	public void testSpamDetectionTemplates() {
		KieSession ksession = this.createKieSession();
		
		this.doTest(ksession);
	}

	private void doTest(KieSession ksession){
		
        UserActionEvent event1 = new UserActionEvent(1l, 1.0);
        UserActionEvent event2 = new UserActionEvent(1l, 1.0);
        UserActionEvent event3 = new UserActionEvent(1l, 1.0);
        UserActionEvent event4 = new UserActionEvent(1l, 1.0);

        
        ksession.insert(event1);
        ksession.insert(event2);
        ksession.insert(event3);
        ksession.insert(event4);
        
        int numberOfFiredRules = ksession.fireAllRules();
        assertEquals(numberOfFiredRules, 2);
    }
	
	private KieSession createKieSession() {
        ObjectDataCompiler converter = new ObjectDataCompiler();

    	InputStream spamDetectionTemplate = getClass().getResourceAsStream("/sbnz/integracija/spam-protection.drt");
        
        List<SpamDetectionTemplateModel> spamDetectionData = new ArrayList<SpamDetectionTemplateModel>();
        
        spamDetectionData.add(new SpamDetectionTemplateModel(9, 6, "SPAMMING_COMMENTS"));
        spamDetectionData.add(new SpamDetectionTemplateModel(2, 3, "SPAMMING_BAD_RATING"));
        
        String spamDetectionDRL = converter.compile(spamDetectionData, spamDetectionTemplate);
        
        System.out.println(spamDetectionDRL);
        
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(spamDetectionDRL, ResourceType.DRL);

        InputStream alarmTriggerTemplate = getClass().getResourceAsStream("/sbnz/integracija/alarm.drt");
        
        List<AlarmTriggerTemplateModel> alarmTemplateData = new ArrayList<AlarmTriggerTemplateModel>();
        
        alarmTemplateData.add(new AlarmTriggerTemplateModel(0));
        
        String alarmTriggerDRL = converter.compile(alarmTemplateData, alarmTriggerTemplate);
        
        kieHelper.addContent(alarmTriggerDRL, ResourceType.DRL);

        return kieHelper.build().newKieSession();
    }
}
