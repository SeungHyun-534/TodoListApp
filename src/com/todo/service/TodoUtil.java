package com.todo.service;

import java.io.*;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸� �߰�]\n���� > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("�̹� �����ϴ� �����Դϴ�.");
			return;
		}
		
		System.out.print("���� >");
		sc.nextLine();
		desc = sc.nextLine();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String title = sc.next();
		
		System.out.print("[�׸� ����]\n ������ �׸��� ������ �Է��Ͻÿ� > ");
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[�׸� ����]\n������ �׸��� ������ �Է��Ͻÿ� > ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("�׷� ������ ���� ������ ã�� �� �����ϴ�.");
			return;
		}

		System.out.print("�� ���� > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("�̹� �����ϴ� �����Դϴ�.");
			return;
		}
		
		System.out.print("�� ���� > ");
		sc.nextLine();
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("�����Ǿ����ϴ�.");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[��ü ���]");
		for (TodoItem item : l.getList()) {
			System.out.println("[" + item.getTitle() +"] "+ item.getDesc() + " - "+ item.getCurrent_date());
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter(filename);
			for(TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String oneline;
			while((oneline = reader.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(oneline,"##");
				String title = st.nextToken();
				String desc = st.nextToken();
				String current_date = st.nextToken();
				l.addItem(new TodoItem(title,desc,current_date));
			}
			reader.close();
		}catch(FileNotFoundException e) {
			System.out.println("�о�� ������ ã�� �� �����ϴ�.");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
