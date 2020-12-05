package ru.ystu.dao;import ru.ystu.CSVScanner;import ru.ystu.entity.Answer;import ru.ystu.entity.Queue;import java.io.File;import java.io.IOException;import java.util.ArrayList;import java.util.Arrays;import java.util.List;import java.util.Objects;public class QueueDaoCsv implements QueueDao{	private List<Queue> queueList = new ArrayList<>();	@Override	public Boolean initData() {		File csvFile = new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource("question_file.csv")).getPath());		try {			List<String[]> csvItemList = CSVScanner.goReadFile(csvFile);			List<Queue> listQueue = new ArrayList<>();			for (String[] row : csvItemList) {				String queueString = Arrays.toString(row);				queueString = queueString.substring(1, queueString.length() - 1);				String[] test = queueString.split(",");				if (test.length > 3) {					List<Answer> listAnswer = new ArrayList<>();					for (int i = 2; i < test.length; i++) {						listAnswer.add(new Answer(test[i].trim()));					}					int numberResultQueue = 1;					String resultQueue = test[1];					if (resultQueue.isEmpty()) {						System.out.println("Ошибка чтения вопроса");					} else {						numberResultQueue = Integer.parseInt(resultQueue.trim());					}					String question = test[0].trim();					Queue queue = new Queue(question, numberResultQueue, listAnswer);					listQueue.add(queue);				}			}			queueList = listQueue;			return !queueList.isEmpty();		} catch (IOException e) {			e.printStackTrace();			return false;		}	}	@Override	public List<Answer> getAnswers(int queueId) {		return queueList.get(queueId).getAnswerList();	}	@Override	public int getQueueCount() {		return queueList.size();	}	@Override	public String getNameQueue(int queueId) {		return queueList.get(queueId).getName();	}	@Override	public Boolean isTrueAnswer(int queueId, int answerId) {		return queueList.get(queueId).getResult().equals(answerId);	}}