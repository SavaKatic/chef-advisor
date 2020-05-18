package sbnz.integracija.chefadvisor;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbnz.integracija.chefadvisor.facts.Item;
import sbnz.integracija.chefadvisor.facts.TextObj;

@Service
public class SampleAppService {

	private static Logger log = LoggerFactory.getLogger(SampleAppService.class);

	private final KieContainer kieContainer;

	@Autowired
	public SampleAppService(KieContainer kieContainer) {
		log.info("Initialising a new example session.");
		this.kieContainer = kieContainer;
	}

	public Item getClassifiedItem(Item i) {
    TextObj m = new TextObj();
    KieSession kieSession = kieContainer.newKieSession();
    kieSession.insert(i);
    kieSession.insert(m);
		kieSession.fireAllRules();
    kieSession.dispose();
    System.out.println(m.getChanged());
    System.out.println(i);
		return i;
	}
}
