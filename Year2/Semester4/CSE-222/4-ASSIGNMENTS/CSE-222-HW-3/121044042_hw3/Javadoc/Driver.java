import java.util.*;

/**
 * Driver Class
 *
 * @author Seyda Nur DEMIR
 */
public class Driver {
	void driverForBranch () {
		KWLinkedList <Branch> branches = new KWLinkedList <Branch>();
		System.out.println("\n---------------------------------------------------- Branch Class Driver");
		branches.addLast((new Branch("Darica","Istasyon Cad, No:14")));
		branches.addLast((new Branch("Gebze","Yeni Bagdat Cad, No:75")));
		branches.addLast((new Branch("Emek","Fatih Sultan Mehmet Cad, No:9")));
		branches.addLast((new Branch("Korfez","Istasyon Cad, No:32")));
		branches.addLast((new Branch("Derince","Sahil Cad, No:80")));
		branches.addLast((new Branch("Basiskele","Mahmut Cavus Cad, No:13")));
		branches.addLast((new Branch("Golcuk","Koca Ahmet Cad, No:21")));
		System.out.println("\n---------------------------------- Added Seven Branches to Branches List");
		branches.print();
		System.out.println("\n-------------------------------------------------- Printed Branches List");
		branches.remove(3);
		System.out.println("\n---------------------------- Removed The Third Branch from Branches List");
		branches.removeFirst();
		System.out.println("\n---------------------------- Removed The First Branch from Branches List");
		branches.removeLast();
		System.out.println("\n----------------------------- Removed The Last Branch from Branches List");
		branches.print();
		System.out.println("\n-------------------------------------------------- Printed Branches List");
	}
	void driverForProduct () {
		KWArrayList <Product> products = new KWArrayList <Product>();
		System.out.println("\n---------------------------------------------------- Product Class Driver");
		products.add((new Product("Office Chair","Tulpar","Red")));
		products.add((new Product("Office Chair","Tulpar","Black")));
		products.add((new Product("Office Chair","Tulpar","White")));
		products.add((new Product("Office Desk","Triangle","Brown")));
		products.add((new Product("Meeting Table","O Table","Dark Brown")));
		products.add((new Product("Book Case","Four Roofs","No Color")));
		products.add((new Product("Office Cabinet","Five Doors","No Color")));
		System.out.println("\n---------------------------------- Added Seven Products to Products List");
		products.print();
		System.out.println("\n-------------------------------------------------- Printed Products List");
		products.remove(3);
		System.out.println("\n--------------------------- Removed The Third Product from Products List");
		products.remove(0);
		System.out.println("\n--------------------------- Removed The First Product from Products List");
		products.remove(products.size()-1);
		System.out.println("\n---------------------------- Removed The Last Product from Products List");
		products.print();
		System.out.println("\n-------------------------------------------------- Printed Products List");
	}
	void driverForOrder () {
		KWArrayList <Order> orders = new KWArrayList <Order>();
		System.out.println("\n----------------------------------------------------- Order Class Driver");
		orders.add((new Order((new Administrator((new Branch("Darica","Istasyon Cad, No:14")),"Seyda Nur","DEMIR","sncakar@gtu.edu.tr","12345678","Kocaeli","555 555 55 55")),(new Product("Office Chair","Tulpar","Red")))));
		orders.add((new Order((new BranchEmployee((new Branch("Gebze","Yeni Bagdat Cad, No:75")),"Ayse","KOCAK","ayse@furniture.com","12345678","Kocaeli","111 111 11 11")),(new Product("Office Chair","Tulpar","Black")))));
		orders.add((new Order((new BranchEmployee((new Branch("Emek","Fatih Sultan Mehmet Cad, No:9")),"Fatma","ALTIN","fatma@furniture.tr","12345678","Kocaeli","222 222 22 22")),(new Product("Office Chair","Tulpar","White")))));
		orders.add((new Order((new BranchEmployee((new Branch("Korfez","Istasyon Cad, No:32")),"Ali","KAYA","ali@furniture.tr","12345678","Kocaeli","333 333 33 33")),(new Product("Office Desk","Triangle","Brown")))));
		orders.add((new Order((new Customer((new Branch("Derince","Sahil Cad, No:80")),"Veli","AKAY","veli@furniture.tr","12345678","Kocaeli","333 333 33 33")),(new Product("Meeting Table","O Table","Dark Brown")))));
		orders.add((new Order((new Customer((new Branch("Basiskele","Mahmut Cavus Cad, No:13")),"Akif","BAS","akif@furniture.tr","12345678","Ankara","222 222 22 22")),(new Product("Book Case","Four Roofs","No Color")))));
		orders.add((new Order((new Customer((new Branch("Golcuk","Koca Ahmet Cad, No:21")),"Zeynep","SONMEZ","zeynep@furniture.tr","12345678","Ankara","111 111 11 11")),(new Product("Office Cabinet","Five Doors","No Color")))));
		System.out.println("\n-------------------------------------- Added Seven Orders to Orders List");
		orders.print();
		System.out.println("\n---------------------------------------------------- Printed Orders List");
		orders.remove(3);
		System.out.println("\n------------------------------- Removed The Third Order from Orders List");
		orders.remove(0);
		System.out.println("\n------------------------------- Removed The First Order from Orders List");
		orders.remove(orders.size()-1);
		System.out.println("\n-------------------------------- Removed The Last Order from Orders List");
		orders.print();
		System.out.println("\n---------------------------------------------------- Printed Orders List");
	}
	void driverForCustomer () {
		
	}
	void driverForBranchEmployee () {
		
	}
	void driverForAdministrator () {
		
	}
}
