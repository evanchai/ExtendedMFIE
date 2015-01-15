package cn.edu.fudan.se.data.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import cn.edu.fudan.se.web.util.CommUtil;
import cn.edu.fudan.se.web.util.INIHelper;

public class Indexer {

	// public static String indexPath = "D:/fukun/data/SearchIndex";
	public static String indexPath = "D:/fukun/data/SearchIndexC";
	private static int MAX_NUM = 1 << 15;
	static{
		String path = CommUtil.getCurrentProjectPath()+ "/conf.ini";
		if(path.contains("null")){
			path = SearchSwicher.initFilePath;
		}
		INIHelper iniHelper = new INIHelper(path
				);
		indexPath = iniHelper.getValue("lucence", "path", null);
	}

	private static IndexWriter myWriter = null;

	public static void prepareIndex(String indexDir) {
		try {
			myWriter = makeIndexWriter(indexDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void makeIndex(String[][] datum, byte[] tags, int n) {
		if (myWriter == null) {
			System.out.println("There's no writer.");
			return;
		}
		try {
			Document doc = new Document();
			for (int i = 0; i < n; i++) {
				doc.add(new TextField(datum[i][0], //
						datum[i][1], //
						(tags[i] == 1) ? Field.Store.YES : Field.Store.NO));

			}
			myWriter.addDocument(doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void closeIndex() {
		try {
			myWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myWriter = null;
	}

	private static IndexWriter makeIndexWriter(String path) throws IOException {
		Directory directory = FSDirectory.open(new File(path));
		return new IndexWriter(directory, new IndexWriterConfig(
				Version.LUCENE_46, new StandardAnalyzer(Version.LUCENE_46)));
	}

	public static String[][] search(String dirPath, String keyField,
			String oriKey, String[] retFields) {

		// QueryParser parser = new QueryParser(
		// Version.LUCENE_46, keyField,
		// new StandardAnalyzer(Version.LUCENE_46));
		String key = oriKey.toLowerCase();
		String[] keys = key.split(" ");
		IndexSearcher searcher;
		try {
			searcher = new IndexSearcher(DirectoryReader.open(FSDirectory
					.open(new File(dirPath))));
			// Query query = parser.parse(key);
			BooleanQuery booleanQuery = new BooleanQuery();
			for (String k : keys) {
				Query query = new WildcardQuery(new Term(keyField, "*" + k
						+ "*"));
				booleanQuery.add(query, BooleanClause.Occur.MUST);
			}

			TopDocs topFieldDocs = searcher.search(booleanQuery, MAX_NUM);// 第二个参数是过滤器，此例中不需要
			ScoreDoc[] socDocs = topFieldDocs.scoreDocs;
			// System.out.println("hits:" + topFieldDocs.totalHits);

			List<String[]> retList = new ArrayList<String[]>();

			float score = -1;
			for (int i = 0; i < socDocs.length; i++) {
				int docNum = socDocs[i].doc;
				// ensure the document is the max match one.
				if (score == socDocs[i].score) {

				} else if (score < socDocs[i].score) {
					score = socDocs[i].score;
				} else
					break;
				Document doc = searcher.doc(docNum);
				for (int j = 0; j < retFields.length; j++) {
					String[] rets = new String[retFields.length];
					rets[j] = doc.get(retFields[j]);
					retList.add(rets);
				}
			}
			return retList.toArray(new String[retList.size()][]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String argv[]) {
		// Indexer.makeIndex();
		// System.out.println("done");

		String[][] k = Indexer.search(Indexer.indexPath, "title", "27",
				new String[] { "title" });
		for (int i = 0; i < k.length; i++) {
			for (int j = 0; j < k[0].length; j++) {
				System.out.print(k[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("done");
	}

	public static void search() {

		QueryParser parser = new QueryParser(Version.LUCENE_46, "title",
				new StandardAnalyzer(Version.LUCENE_46));
		IndexSearcher searcher;
		try {
			searcher = new IndexSearcher(DirectoryReader.open(FSDirectory
					.open(new File(indexPath))));
			String keyword = "lucene";
			Query query = parser.parse(keyword);

			TopDocs topFieldDocs = searcher.search(query, MAX_NUM);
			ScoreDoc[] socDocs = topFieldDocs.scoreDocs;
			System.out.println("hits:" + topFieldDocs.totalHits);
			for (ScoreDoc scoreDoc : socDocs) {
				int docNum = scoreDoc.doc;
				Document doc = searcher.doc(docNum);
				String text = doc.get("title");
				float score = scoreDoc.score;
				System.out.println(text + " " + score);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void makeIndex() {
		IndexWriter writer;
		try {
			writer = makeIndexWriter(indexPath);
			for (int i = 0; i < 100; i++) {
				Document doc = new Document();
				doc.add(new TextField("title", "lucene introduction.Highlight "
						+ i, Field.Store.YES));

				writer.addDocument(doc);
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
