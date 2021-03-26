package logic;

import java.util.Random;
import java.util.concurrent.ScheduledFuture;

import com.smartfoxserver.v2.api.ISFSMMOApi;

public class MUFRUserRunner implements Runnable{
	
	private ScheduledFuture<?> userRunnerTask;
	private ISFSMMOApi mmoAPi;
	
    Random randomGenerator = new Random();
	
	float distance;

	public MUFRUserRunner(ISFSMMOApi mmoAPi) {
		super();
		this.mmoAPi = mmoAPi;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
