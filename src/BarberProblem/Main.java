package BarberProblem;

public class Main {
	public static void main(String[] args) {
		BarberShop shop = new BarberShop(4);       // waiting room have total 4 chair

        // Create and start barber thread
        Thread barberThread = new Thread(() -> shop.barberWorks());
        barberThread.start();

        // Create and start customer threads
        for (int i = 1; i <= 8; i++) {
            int customerId = i;
            new Thread(() -> shop.customerArrives(customerId)).start();
            try {
                Thread.sleep((int) (Math.random() * 100) + 200); // Random arrival time for customers
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
}
