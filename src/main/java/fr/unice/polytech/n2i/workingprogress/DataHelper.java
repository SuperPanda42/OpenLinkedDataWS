package fr.unice.polytech.n2i.workingprogress;

import com.hp.hpl.jena.query.*;
import fr.unice.polytech.n2i.workingprogress.model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by clement0210 on 14-12-04.
 */
public class DataHelper {


    private static Question questionCasualties;

    public static Question getQuestion() {
        int rand = new Random().nextInt(4);
        switch (rand) {
            case 0:
                return getQuestionGDP();
            case 1:
                return getQuestionNumberOfPeople();
            case 2:
                return getQuestionHDI();
            case 3:
                return getQuestionCasualties();
            default:
                return getQuestionGDP();
        }
    }

    private static Question getQuestionNumberOfPeople() {
        String query2 = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
                "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n" +
                "PREFIX : <http://dbpedia.org/resource/>\n" +
                "PREFIX prop: <http://dbpedia.org/property/>\n" +
                "PREFIX dbpedia: <http://dbpedia.org/>\n" +
                "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "\n" +
                "select * \n" +
                "where {\n" +
                "  service <http://dbpedia.org/sparql/> {\n" +
                "     select ?country ?nb where {\n" +
                "\n" +
                "       ?c prop:populationCensus ?nb;\n" +
                "           prop:commonName ?country.\n" +
                "       \n" +
                "       \t\n" +
                "     } \n" +
                "     limit 100\n" +
                "  }\n" +
                "}\n";
        return queryAQuestionNumberOfPeople(query2);
    }

    private static Question queryAQuestionNumberOfPeople(String queryString) {
        // now creating query object
        Query query = QueryFactory.create(queryString);
        // initializing queryExecution factory with remote service.
        // **this actually was the main problem I couldn't figure out.**
        QueryExecution qexec = QueryExecutionFactory.create(query, DatasetFactory.createMem());
        //after it goes standard query execution and result processing which can
        // be found in almost any Jena/SPARQL tutorial.
        try {
            List<Question> questions = new ArrayList<Question>();
            ResultSet results = qexec.execSelect();
            for (; results.hasNext(); ) {
                QuerySolution solution = results.next();
                String str2 = solution.get("?nb").asLiteral().getString();
                String str = solution.get("?country").asLiteral().getString();
                // Result processing is done here.
                Question question = new Question();
                question.setQuestion("What is the population census in " + str + " ?");
                question.setRightAnswer(str2);
                questions.add(question);
            }
            int rand = new Random().nextInt(questions.size());

            Question question = questions.get(rand);

            List<String> answers = new ArrayList<String>();
            answers.add(question.getRightAnswer());
            for (int i = 0; i < 4; i++) {
                rand = new Random().nextInt(questions.size());
                answers.add(questions.get(rand).getRightAnswer());
            }
            Collections.shuffle(answers);
            question.setAnswers(answers);
            return question;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            qexec.close();
        }
        return new Question();

    }


    private static Question getQuestionGDP() {
        String query2 = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
                "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n" +
                "PREFIX : <http://dbpedia.org/resource/>\n" +
                "PREFIX prop: <http://dbpedia.org/property/>\n" +
                "PREFIX dbpedia: <http://dbpedia.org/>\n" +
                "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "\n" +
                "select * \n" +
                "where {\n" +
                "  service <http://dbpedia.org/sparql/> {\n" +
                "     select ?country ?pib where {\n" +
                "\n" +
                "       ?c prop:gdpNominal ?pib;\n" +
                "          prop:commonName ?country.\n" +
                "     } \n" +
                "     limit 100\n" +
                "  }\n" +
                "}\n";
        return queryAQuestionGDP(query2);


    }

    private static Question queryAQuestionGDP(String queryString) {
        // now creating query object
        Query query = QueryFactory.create(queryString);
        // initializing queryExecution factory with remote service.
        // **this actually was the main problem I couldn't figure out.**
        QueryExecution qexec = QueryExecutionFactory.create(query, DatasetFactory.createMem());
        //after it goes standard query execution and result processing which can
        // be found in almost any Jena/SPARQL tutorial.
        try {
            List<Question> questions = new ArrayList<Question>();
            ResultSet results = qexec.execSelect();
            for (; results.hasNext(); ) {
                QuerySolution solution = results.next();
                String str2 = solution.get("?pib").asLiteral().getString();
                String str = solution.get("?country").asLiteral().getString();
                // Result processing is done here.
                Question question = new Question();
                question.setQuestion("What is the Gross Domestic Product for " + str + " ?");
                question.setRightAnswer(str2);
                questions.add(question);
            }
            int rand = new Random().nextInt(questions.size());

            Question question = questions.get(rand);
            while (!question.getRightAnswer().contains("E")) {
                rand = new Random().nextInt() * questions.size();
                question = questions.get(rand);
            }
            List<String> answers = new ArrayList<String>();
            for (int i = -2; i < 2; i++) {
                if (question.getRightAnswer().contains("E")) {
                    String[] spliter = question.getRightAnswer().split("E");
                    answers.add(spliter[0] + "E" + (Integer.parseInt(spliter[1]) + i));
                }

            }
            Collections.shuffle(answers);
            question.setAnswers(answers);
            return question;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            qexec.close();
        }
        return new Question();
    }

    public static Question getQuestionHDI() {
        String query2 = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
                "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n" +
                "PREFIX : <http://dbpedia.org/resource/>\n" +
                "PREFIX prop: <http://dbpedia.org/property/>\n" +
                "PREFIX dbpedia: <http://dbpedia.org/>\n" +
                "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "\n" +
                "select * \n" +
                "where {\n" +
                "  service <http://dbpedia.org/sparql/> {\n" +
                "     select ?country ?hdi where {\n" +
                "\n" +
                "       ?c prop:hdi ?hdi;\n" +
                "          prop:commonName ?country.\n" +
                "     } \n" +
                "     limit 100\n" +
                "  }\n" +
                "}\n";
        return queryAQuestionHDI(query2);
    }

    private static Question queryAQuestionHDI(String queryString) {
        // now creating query object
        Query query = QueryFactory.create(queryString);
        // initializing queryExecution factory with remote service.
        // **this actually was the main problem I couldn't figure out.**
        QueryExecution qexec = QueryExecutionFactory.create(query, DatasetFactory.createMem());
        //after it goes standard query execution and result processing which can
        // be found in almost any Jena/SPARQL tutorial.
        try {
            List<Question> questions = new ArrayList<Question>();
            ResultSet results = qexec.execSelect();
            for (; results.hasNext(); ) {
                QuerySolution solution = results.next();
                String str2 = solution.get("?hdi").asLiteral().getString();
                String str = solution.get("?country").asLiteral().getString();
                // Result processing is done here.
                Question question = new Question();
                question.setQuestion("What is the human development index for " + str + " ?");
                question.setRightAnswer(str2);
                questions.add(question);
            }
            int rand = new Random().nextInt(questions.size());

            Question question = questions.get(rand);

            List<String> answers = new ArrayList<String>();
            answers.add(question.getRightAnswer());
            for (int i = 0; i < 4; i++) {
                rand = new Random().nextInt(questions.size());
                answers.add(questions.get(rand).getRightAnswer());
            }
            Collections.shuffle(answers);
            question.setAnswers(answers);
            return question;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            qexec.close();
        }
        return new Question();
    }

    public static Question getQuestionCasualties() {
        String query2 = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
                "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n" +
                "PREFIX : <http://dbpedia.org/resource/>\n" +
                "PREFIX prop: <http://dbpedia.org/property/>\n" +
                "PREFIX ont: <http://dbpedia.org/ontology/>\n" +
                "PREFIX dbpedia: <http://dbpedia.org/>\n" +
                "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "\n" +
                "select * \n" +
                "where {\n" +
                "  service <http://dbpedia.org/sparql/> {\n" +
                "     select (max(?nb) as ?number)  ?name where {\n" +
                "       ?c rdf:type ont:MilitaryConflict;\n" +
                "           prop:casualties ?nb.\n" +
                "      ?c foaf:name ?name.\n" +
                "       Filter ( datatype(?nb) = xsd:integer && ?nb >= 10000)\n" +
                "     } \n" +
                "group by ?name\n" +
                "     limit 100\n" +
                "  }\n" +
                "}\n";
        return queryAQuestionCasualties(query2);
    }

    private static Question queryAQuestionCasualties(String queryString) {
        // now creating query object
        Query query = QueryFactory.create(queryString);
        // initializing queryExecution factory with remote service.
        // **this actually was the main problem I couldn't figure out.**
        QueryExecution qexec = QueryExecutionFactory.create(query, DatasetFactory.createMem());
        //after it goes standard query execution and result processing which can
        // be found in almost any Jena/SPARQL tutorial.
        try {
            List<Question> questions = new ArrayList<Question>();
            ResultSet results = qexec.execSelect();
            for (; results.hasNext(); ) {
                QuerySolution solution = results.next();
                String str2 = solution.get("?number").asLiteral().getString();
                String str = solution.get("?name").asLiteral().getString();
                // Result processing is done here.
                Question question = new Question();
                question.setQuestion("What are the casualties fo the war " + str + " at least ?");
                question.setRightAnswer(str2);
                questions.add(question);
            }
            int rand = new Random().nextInt(questions.size());

            Question question = questions.get(rand);

            List<String> answers = new ArrayList<String>();
            answers.add(question.getRightAnswer());
            for (int i = 0; i < 4; i++) {
                rand = new Random().nextInt(questions.size());
                answers.add(questions.get(rand).getRightAnswer());
            }
            Collections.shuffle(answers);
            question.setAnswers(answers);
            return question;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            qexec.close();
        }
        return new Question();
    }
}
