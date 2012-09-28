package ss.pku.re.rule.util;

import java.io.IOException;
import java.io.Reader;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatefulSession;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
/**
 * 暂时废弃了这个方法
 * @author lqs
 *
 */
public class KnowledgeSessionFactory {
	private static StatefulKnowledgeSession kSession;            //适用于从drl文件中生成规则的情况
	private static StatefulSession rSession;                 //适用于动态生成rule的情况
	private KnowledgeSessionFactory() {

	}

	/**
	 * 保证kSession的单例形式
	 * @param drls
	 * @return
	 * @throws Exception 
	 */
	public static StatefulKnowledgeSession getSession(String... drls){
		if(kSession==null)
			kSession =  readKnowledgeBase(drls).newStatefulKnowledgeSession();
		return kSession;
	}
	
	public static StatefulSession getSession(Reader...readers) throws DroolsParserException, IOException{
		if(rSession==null)
			rSession = readRuleBase(readers).newStatefulSession();
		return rSession;	
	}
	/**
	 * 
	 * @param drls
	 * @throws Exception
	 */
	private static KnowledgeBase readKnowledgeBase(String...drls){
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		for(String drl:drls){
			kbuilder.add(ResourceFactory.newClassPathResource(drl),
				ResourceType.DRL);
		}
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error : errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		KnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();
		kBase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		return kBase;
	}
	/**
	 * 
	 * @param readers
	 * @throws IOException 
	 * @throws DroolsParserException 
	 * @throws Exception
	 */
	private static RuleBase readRuleBase(Reader...readers) throws DroolsParserException, IOException{
		PackageBuilder pbuilder = new PackageBuilder();
		for(Reader reader:readers){
			pbuilder.addPackageFromDrl(reader);
		}
		RuleBase rBase = RuleBaseFactory.newRuleBase();
		rBase.addPackage(pbuilder.getPackage());
		return rBase;
	}
}
