package form;import ru.ystu.nx.service.QueueServiceImpl;public class MainFormImpl implements MainForm {	private final QueueServiceImpl queueService;	public MainFormImpl(QueueServiceImpl queueService) {		this.queueService = queueService;	}	@Override	public void startQuiz() {		queueService.run();	}}