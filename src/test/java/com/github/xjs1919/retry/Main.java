/**
 * 
 */
package com.github.xjs1919.retry;

import java.util.Random;

import com.github.xjs1919.retry.RetryAble;
import com.github.xjs1919.retry.RetryService;
import com.github.xjs1919.retry.RetryTask;
import com.github.xjs1919.retry.RetryService.OnRetryListener;

/**
 * @author 605162215@qq.com
 *
 * 2016��8��6�� ����1:05:30
 */
public class Main {
	public static class Business implements RetryAble{
		public boolean retry() throws Exception{
			try{
				System.out.println("[Business]do business...");
				Thread.sleep(1000);
				int rnd = new Random().nextInt(100);
				System.out.println("[Business]rnd:"+rnd);
				if(rnd > 80){
					System.out.println("[Business]do business end");
					return true;
				}else{
					throw new Exception();
				}
			}catch(Exception e){
				System.out.println("[Business]do business exception!");
				throw e;
			}
		}
	}
	public static void main(String[] args) {
		//����service
		RetryService service = new RetryService(new int[]{0,0,0}, new RetryPersistService());
		//����service
		service.start(new OnRetryListener(){
			//ÿ�������������Ժ󶼻�ص�
			@Override
			public void onRetryArrived(RetryTask retryTask) {
				System.out.println("[main]onDelayedArrived:"+retryTask);
			}
			//����ʧ���˵�ʱ���ص���ֻһ��
			@Override
			public void onRetryFailed(RetryTask retryTask){
				System.out.println("[main]onRetryFailed");
			}
			//���ճɹ��˵�ʱ���ص���ֻһ��
			@Override
	        public void onRetrySuccessed(RetryTask retryTask){
	        	System.out.println("[main]onRetrySuccessed");
	        }
		});
		//��ҵ���߼�����
		Business business = new Business();
		try{
			business.retry();
		}catch(Exception e){
			System.out.println("[main]business ecxception, try redo");
			//ʧ������
			service.add(business);
		}
	}
}
