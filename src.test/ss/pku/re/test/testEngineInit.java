package ss.pku.re.test;

import org.junit.Test;

import ss.pku.re.rule.util.DynamicallyAddRules;
import ss.pku.re.rule.util.RuleEngineInit;


public class testEngineInit {
	@Test
	public void test(){
		//RuleEngineInit.RuleEnineInitere("d:/test2");
		String filepath="D:\\test2";
		//String filename=RuleEngineInit.RemoveAtEnd(filepath.substring(filepath.lastIndexOf("\\")+1, filepath.length()),".xml");
		DynamicallyAddRules ad=new DynamicallyAddRules();
		RuleEngineInit.RuleEnineIniter(filepath);
	//	ad.AddXml(filepath);
	//	System.out.println(filename);
	}

}
