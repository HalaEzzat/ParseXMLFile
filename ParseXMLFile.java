import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class ParseXMLFile {
  private String infilePath;
  private String outfilePath;
  private String tagName;
  private List<String> tags;
 
  public ParseXMLFile(String infilePath,String tagName,String outfilePath){
	this.infilePath=infilePath;
	this.tagName=tagName;
        this.outfilePath=outfilePath;
  }
  
  public void setInFilePath(String path){infilePath=path;}
  public void setOutFilePath(String path){outfilePath=path;}
  public void setTagName(String tag){tagName=tag;}
  public void setTagList(List<String> mytags){this.tags= new ArrayList<>();this.tags.addAll(mytags);}

  public String getInFilePath(){return this.infilePath;}
  public String getOutFilePath(){return this.outfilePath;}
  public String getTagName(){return this.tagName;}
  public List<String> getTagList(){return this.tags;}

  public boolean parseFile(){
    boolean flag=false;
    try {
		File fXmlFile = new File(this.infilePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName(this.tagName);		
        	try{
        		FileWriter fileWriter = new FileWriter(this.outfilePath);
        		PrintWriter printWriter = new PrintWriter(fileWriter);
        		printWriter.print("+-----------------------------------------------------------------------------------+\n");
                        for(String str:this.tags){           
				 printWriter.print("|");
            			printWriter.format("%20s",str);
        		}
        		printWriter.print("|\n+-----------------------------------------------------------------------------------+\n");
			for (int temp = 0; temp < nList.getLength(); temp++) 
			{
				Node nNode = nList.item(temp);	
				if (nNode.getNodeType() == Node.ELEMENT_NODE) 
				{
					Element eElement = (Element) nNode;
 					for(String str:this.tags){ 
						printWriter.print("|");
            					printWriter.format("%20s",eElement.getElementsByTagName(str).item(0).getTextContent());
        				}
                        		printWriter.print("|\n+-----------------------------------------------------------------------------------+\n");
				}
			}
        		printWriter.close();
        		flag=true;
      		}catch(Exception e)
		{
	    		System.out.println("problem with creating the output file");
            		flag=false;
     		}
    	} catch (Exception e) {
	e.printStackTrace();
        flag=false;
    }
     return flag;
  
  }
  public static void main(String argv[]) {
	ParseXMLFile xmlf=new ParseXMLFile("file.xml","staff","out.txt");
        List <String> tags= new ArrayList<>();
        tags.add("firstname");
	tags.add("lastname");
	tags.add("nickname");
	tags.add("salary");
        xmlf.setTagList(tags);
        boolean result=xmlf.parseFile();
        if (result==true){System.out.println("successfully parsed the file to "+xmlf.getOutFilePath());}
        else {System.out.println("there was an error,couldn't parse the file");}
}
}
