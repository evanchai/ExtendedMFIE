package cn.edu.fudan.se.cluster;

import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.carrot2.clustering.kmeans.BisectingKMeansClusteringAlgorithm;
import org.carrot2.clustering.lingo.LingoClusteringAlgorithm;
import org.carrot2.clustering.stc.STCClusteringAlgorithm;
import org.carrot2.clustering.synthetic.ByUrlClusteringAlgorithm;
import org.carrot2.core.Cluster;
import org.carrot2.core.Controller;
import org.carrot2.core.ControllerFactory;
import org.carrot2.core.Document;
import org.carrot2.core.ProcessingResult;
import org.jsoup.Jsoup;

import com.stackoverflow.bean.Post;
import com.stackoverflow.utils.PostDAOImpl;


import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;

public class MultiFacets {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		PostDAOImpl postDAO = new PostDAOImpl();
		List<Post> postList = postDAO.findPosts("database");
//		int i=0;
//		StringBuffer sb= new StringBuffer ();
//		for(Post p:postList){
//			String  text=p.getPost_tag()+p.getPost_title()+p.getPost_body();
//			//if(!text.contains("table")&&!text.contains("query"))
//				//i++;
//		}
//		System.out.println(i);
		clusteringByContent(postList);
		clusteringByTag(postList);
		//clusteringByProgarmmingLanuguage(postList);
		//String text = "It 's the first group objective-c against.The CRF code is by Jenny Finkel. The feature extractors are by Dan Klein, Christopher Manning, and Jenny Finkel.";
		//stanfordNLPPreProcess(text);
	}

	public static String stanfordNLPPreProcess(String text) throws Exception {
		text.toLowerCase();
		String Stem="";
		
		Properties props = new Properties();
		props.put("annotators", "tokenize,ssplit,pos,lemma");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Annotation document = new Annotation(text);
		pipeline.annotate(document);
		
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for(CoreMap sentence:sentences)
		{
			for(CoreLabel token: sentence.get(TokensAnnotation.class))
			{
				//tokenize
				String word = token.get(TextAnnotation.class);
				//stem
				String stem = token.get(LemmaAnnotation.class);
				Stem +=stem+" ";
			}
		}
		System.out.println(Stem);
		return Stem;
	}

	public static List<Cluster> clusteringByContent(List<Post> postList)
			throws Exception {
		/* Prepare Carrot2 documents */
		final ArrayList<Document> documents = new ArrayList<Document>();
		for (Post post : postList) {
				Document doc = new Document(post.post_title,
						post.getPost_body_text(), "Description");
				doc.setField("post", post);
				documents.add(doc);
		}
		/* A controller to manage the processing pipeline. */
		final Controller controller = ControllerFactory.createSimple();
		/*
		 * Perform clustering by topic using the Lingo algorithm. Lingo can take
		 * advantage of the original query, so we provide it along with the
		 * documents.
		 */
		final ProcessingResult byContentClusters = controller.process(
				documents, "database", LingoClusteringAlgorithm.class);
		// BisectingKMeansClusteringAlgorithm,LingoClusteringAlgorithm,STCClusteringAlgorithm
		final List<Cluster> clustersByContent = byContentClusters.getClusters();
		// [[[end:clustering-document-list]]]

//		ConsoleFormatter.displayClusters(clustersByContent);
		return clustersByContent;
	}

	public static List<Cluster> clusteringByTag(List<Post> postList) throws Exception {
		/* Prepare Carrot2 documents */
		final ArrayList<Document> documents = new ArrayList<Document>();
		documents.clear();
		for (Post post : postList) {
				Document doc = new Document(post.getPost_tag(), "Summary",
						post.post_tag);
				doc.setField("post", post);
				documents.add(doc);
			}

		/* A controller to manage the processing pipeline. */
		final Controller controller = ControllerFactory.createSimple();
		final ProcessingResult byTagClusters = controller.process(documents,
				null, LingoClusteringAlgorithm.class);
		final List<Cluster> clustersByTag = byTagClusters.getClusters();
//		ConsoleFormatter.displayClusters(clustersByTag);
		return clustersByTag;
	}

	public static List<Cluster> clusteringByProgarmmingLanuguage(List<Post> postList)
			throws Exception {
		String[] languageSet = { "java", "javascript", "c#", "php", "jquery",
				"python", "html", "c++", "" + "css", "objective-c", "c",
				"ruby", "ajax", "regex", "xml", "json", "wpf", "r" };

		/* Prepare Carrot2 documents */
		final ArrayList<Document> documents = new ArrayList<Document>();
		for (Post post : postList) {
				Document doc = new Document(post.post_title, post.post_body,
						post.post_tag);
				doc.setField("post", post);
				documents.add(doc);
		}

		List<Cluster> clustersByLanguage = new ArrayList<Cluster>();
		for (String language : languageSet) {
			Cluster cluster = new Cluster();
			for (Document document : documents) {
				if (!document.getTitle().isEmpty()
						&& (document.getTitle() + document.getContentUrl() + document
								.getSummary()).contains(language)) {
					cluster.addDocuments(document);
				}
			}
			if (cluster.getAllDocuments().size() > 0) {
				clustersByLanguage.add(cluster);
				cluster.addPhrases(language);
			}
		}

		return clustersByLanguage;
		// display
//    	ConsoleFormatter.displayClusters(clustersByLanguage);
	}
}
