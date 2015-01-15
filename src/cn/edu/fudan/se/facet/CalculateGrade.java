package cn.edu.fudan.se.facet;

import cn.edu.fudan.se.NLP.Clause;
import cn.edu.fudan.se.NLP.Object;
import cn.edu.fudan.se.NLP.Predicate;
import cn.edu.fudan.se.NLP.Subject;
import cn.edu.fudan.se.NLP.WordProperty;

import com.stackoverflow.bean.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;


public class CalculateGrade {
    public boolean isSubject(List<WordProperty> subL, Clause queryClause) {
        List<WordProperty> queryL = queryClause.getSubject().getSubjectList();
        int subNum = subL.size();
        int queryNum = queryL.size();

        if ((subNum == 0) && (queryNum == 0)) {
            return true;
        } else if (((subNum == 0) && (queryNum != 0)) ||
                ((subNum != 0) && (queryNum == 0))) {
            return false;
        } else {
            for (WordProperty wp1 : subL) {
                if (!wp1.getProperty().contains("N")) {
                    continue;
                }

                for (WordProperty wp2 : queryL) {
                    if (!wp2.getProperty().contains("N")) {
                        continue;
                    }

                    if (wp1.getLemmaWord().equalsIgnoreCase(wp2.getLemmaWord())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isObject(List<WordProperty> objL, Clause queryClause) {
        List<WordProperty> queryL = queryClause.getSubject().getSubjectList();
        int objNum = objL.size();
        int queryNum = queryL.size();

        if ((objNum == 0) && (queryNum == 0)) {
            return true;
        } else if (((objNum == 0) && (queryNum != 0)) ||
                ((objNum != 0) && (queryNum == 0))) {
            return false;
        } else {
            for (WordProperty wp1 : objL) {
                if (!wp1.getProperty().contains("N")) {
                    continue;
                }

                for (WordProperty wp2 : queryL) {
                    if (!wp2.getProperty().contains("N")) {
                        continue;
                    }

                    if (wp1.getLemmaWord().equalsIgnoreCase(wp2.getLemmaWord())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isPredicate(String synonyms1, Clause clause) {
        String[] s1 = synonyms1.split(",");
        String[] s2 = clause.getPredicate().getSynonyms().split(",");

        for (String syn1 : s1) {
            for (String syn2 : s2) {
                if (syn1.equals(syn2)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isAccept(Clause clause, Clause query) {
        if (clause.getAuthority().equals(query.getAuthority())) {
            return true;
        }

        return false;
    }

    public float Grade(Subject subE, Predicate preE, Object objE,
        Clause clause, Clause query) {
        int grade = 0;
        boolean isSub;
        boolean isObj;
        boolean isPre;
        boolean isAccept;
        isSub = isSubject(subE.getSubjectList(), query);
        isObj = isObject(objE.getobjectList(), query);
        isPre = isPredicate(preE.getSynonyms(), query);
        isAccept = isAccept(clause, query);

        if (isSub && isObj && isPre && isAccept) {
            grade = 4;
        } else if ((isSub && isPre && isAccept) ||
                (isObj && isPre && isAccept) || (isSub && isPre && isAccept)) {
            grade = 3;
        } else if ((isSub && isAccept) || (isPre && isAccept)) {
            grade = 2;
        } else if (isAccept) {
            grade = 1;
        } else {
            grade = 0;
        }

        return grade;
    }
    
}
