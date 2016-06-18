import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

class PathPair{
	
	public PathPair(String makePath, String srcPath){
		if(!makePath.isEmpty()){
			mMakePath = makePath;
			mMakePath = mMakePath.replace('\\', '/');
			char last = mMakePath.charAt(mMakePath.length() - 1);
			if(last != '/')
				mMakePath = mMakePath + '/';
		}
		mSrcPath = srcPath;
		mSrcPath = mSrcPath.replace('\\', '/');
	}
	
	public String getMakePath(){
		return mMakePath;
	}
	
	public String getSrcPath(){
		return mSrcPath;
	}
	
	protected String mMakePath;
	
	protected String mSrcPath;
}

public class MakeTool {

	public MakeTool(){
		mPaths = new ArrayList<PathPair>();
		mOutContent = new StringBuilder();
	}
	
	public void readConfig(){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 	
		
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse("config.xml");   
			NodeList nodeList = doc.getElementsByTagName("path");

			for(int i = 0; i < nodeList.getLength(); i++){
				Node pathNode = nodeList.item(i);	
				Element path = (Element)pathNode;
				String makePath = path.getAttribute("makePath");
				String srcPath = path.getAttribute("srcPath");
				mPaths.add(new PathPair(makePath, srcPath));
				
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void genMakeFile() throws IOException{
		FileOutputStream fos = new FileOutputStream("make.txt");
		
		for(int i = 0;  i < mPaths.size(); i++){
			PathPair pathPair = mPaths.get(i);
			String makePath = pathPair.getMakePath();
			String fullPath = makePath  +  pathPair.getSrcPath();
			File dir = new File(fullPath);
			if(!dir.isDirectory())
				continue;
			
			Set<String> setDir = new HashSet<String>();
			Set<String> setSrc = new HashSet<String>();
			
			File[] subFiles = dir.listFiles();
			for(int j = 0; j < subFiles.length; j++)
				this.genOutContent(setDir, setSrc, makePath, subFiles[j]);
			
			String tip="开始写入 "+fullPath+ "的LOCAL_SRC_FILES和LOCAL_C_INCLUDES\r\n下面部分为LOCAL_SRC_FILES,请自行复制\r\n";
			fos.write(tip.getBytes());
			
			String whiteSpace = "              ";
			String br = " \\\r\n";
			Iterator<String> srcBegin = setSrc.iterator();
			while(srcBegin.hasNext()){		
				String srcPath = srcBegin.next();
				srcPath = srcPath.replace('\\', '/');
				fos.write(whiteSpace.getBytes());
				fos.write(srcPath.getBytes());
				fos.write(br.getBytes());
			}	
			
			tip = "下面部分为LOCAL_C_INCLUDES，请自行复制\r\n";
			fos.write(tip.getBytes());
		
			Iterator<String> dirBegin = setDir.iterator();
			while(dirBegin.hasNext()){			
				String dirPath = dirBegin.next();
				dirPath = dirPath.replace('\\', '/');
				fos.write(whiteSpace.getBytes());
				fos.write(dirPath.getBytes());
				fos.write(br.getBytes());
			}
		}
		fos.close();
	}
	
	protected void genOutContent(Set<String> setDir, Set<String> setSrc, String makeFilePath,  File file){
		String path = file.getPath();
		String subPath = path.substring(makeFilePath.length());

		if(file.isDirectory())
		{
			String name = file.getName();
			if(name.equals("win32") || name.equals("ios"))
				return;
			setDir.add(subPath);		
			File[] subFiles = file.listFiles();
			for(int j = 0; j < subFiles.length; j++)
				this.genOutContent(setDir, setSrc, makeFilePath, subFiles[j]);
			return;
		}
		
		if(subPath.contains(".c") || subPath.contains(".cpp"))
			setSrc.add(subPath);
	}
	
	protected ArrayList<PathPair> mPaths;
	
	protected StringBuilder mOutContent;
	
	public static void main(String[] args){
		
		MakeTool tool = new MakeTool();
		tool.readConfig();
		try {
			tool.genMakeFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
