package ru.ystu.nx.service;import ru.ystu.nx.dao.QueueDaoCsv;import ru.ystu.nx.entity.Answer;import java.util.List;import java.util.Scanner;import static java.lang.System.*;public class QueueServiceImpl implements QueueService {	private final QueueDaoCsv queueDaoCsv = new QueueDaoCsv();	private int queueId = 0;	private int countTrueAnswer = 0;	@Override	public void run() {		if (queueDaoCsv.initData()) {			goNextQueue();		}	}	private void goNextQueue() {		if (queueDaoCsv.getQueueCount() > queueId) {			printQuestion();			readAnswer();		} else {			out.println("Отвеченных вопросов: " + countTrueAnswer + " из " + queueDaoCsv.getQueueCount() +					"\nВопросы закончились... " + "Викторина " + "завершена" + ".");		}	}	private void readAnswer() {		Scanner scanner = new Scanner(in);		int answerId = scanner.nextInt();		if (queueDaoCsv.isTrueAnswer(queueId, answerId)) {			handleSuccessAnswer();		} else {			handleErrorAnswer();		}	}	private void printQuestion() {		String nameQuestion = queueDaoCsv.getNameQueue(queueId);		out.println("Вопрос " + (queueId + 1) + " : " + nameQuestion);		printVariantsAnswer();	}	private void printVariantsAnswer() {		List<Answer> answerList = queueDaoCsv.getAnswers(queueId);		for (int id = 0; id < answerList.size(); id++) {			out.println(id + 1 + ". " + answerList.get(id).getName());		}	}	private void handleSuccessAnswer() {		countTrueAnswer++;		if (queueDaoCsv.getQueueCount() - 1 > queueId) {			out.println("Правильный ответ! Следующий вопрос:\n");			queueId++;			goNextQueue();		} else {			out.println("Правильный ответ!\nОтвеченных вопросов: " + countTrueAnswer + " из " + queueDaoCsv.getQueueCount() + "\nВикторина завершена, " +					"ответить на вопросы еще раз?\n1.Да\n2.Нет,закончить");			Scanner scanner = new Scanner(in);			int variantId = scanner.nextInt();			if (variantId == 1) {				queueId = 0;				countTrueAnswer = 0;				goNextQueue();			} else {				exit(0);			}		}	}	private void handleErrorAnswer() {		out.println("Неправильный ответ!\n1.Повторить вопрос\n2.Следующий вопрос\n3.Начать сначала");		Scanner scanner = new Scanner(in);		int variantId = scanner.nextInt();		if (variantId == 1) {			goNextQueue();		} else if (variantId == 2) {			queueId++;			goNextQueue();		} else if (variantId == 3) {			queueId = 0;			countTrueAnswer = 0;			goNextQueue();		} else {			handleErrorAnswer();		}	}}