package BarberProblem;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class BarberShop {
	private Semaphore barber;
	private Semaphore customers;
	private Semaphore accessSeats;
	private int numChairs;
	private int waitingCustomers;
	
	public BarberShop(int numChairs) {
        this.numChairs = numChairs;
        this.customers = new Semaphore(0);
        this.barber = new Semaphore(0);
        this.accessSeats = new Semaphore(1);
        this.waitingCustomers = 0;
    }
	
	public void customerArrives(int customerId) {	
		try {
			accessSeats.acquire();
			if (waitingCustomers < numChairs) {	
                System.out.println("Customer " + customerId + " is waiting.");   
				waitingCustomers++;				
				customers.release();
			} else {
				System.out.println("Customer " + customerId + " leaves as there are no empty chairs.");
				return;
			}
		} catch (InterruptedException ie) {
			
		} finally {
			accessSeats.release();
		}
		try {
			barber.acquire();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
	
	public void barberWorks() {
        while (true) {
        	
            try {
                customers.acquire();  // Thợ cắt tóc chờ cho đến khi có khách hàng
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {           	
                accessSeats.acquire();
            	waitingCustomers--;
                 
                System.out.println("Barber is cutting hair. Waiting customers: " + waitingCustomers);
                barber.release();
            } catch (InterruptedException ie) {
            	
            } finally {
                accessSeats.release();
            }     
            
            try {
                // Simulate time taken for a haircut
                Thread.sleep((int) (Math.random() * 1000) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}

	public static void Log (String msg) {
		System.out.println(msg);
	}
}
