package sms;

import java.util.ArrayList;
import java.util.List;

// GENERIC REPOSITORY CLASS
class Repository<T> {
    private List<T> items;

    // CONSTRUCTORS
    public Repository() {
        this.items = new ArrayList<>();
    }

    // ADD
    public void add(T item) {
        if (item == null) {
            System.out.println("Error: Cannot add null item.");
            return;
        }
        items.add(item);
        System.out.println(item + " added to the repository.");
    }

    // REMOVE
    public void remove(T item) {
        if (items.contains(item)) {
            items.remove(item);
            System.out.println(item + " removed from the repository.");
        } else {
            System.out.println("Error: Item not found in the repository.");
        }
    }

    // GET ALL
    public List<T> getAll() {
        List<T> allItems = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            allItems.add(items.get(i));
        }
        return allItems;
    }

    // SET ITEM
    public void setItems(List<T> items) {
        if (items == null) {
            System.out.println("Error: Cannot set null items.");
        } else {
            this.items = new ArrayList<>(items);
            System.out.println("Repository updated with new items.");
        }
    }

    // DISPLAY ALL
    public void displayAll() {
        if (items.isEmpty()) {
            System.out.println("Repository is empty.");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            T obj = items.get(i);
            if (obj instanceof Student) {
                Student student = (Student) obj;
                System.out.println("Student: " + student);
            } else if (obj instanceof Teacher) {
                Teacher teacher = (Teacher) obj;
                System.out.println("Teacher: " + teacher);
            } else if (obj instanceof Course) {
                Course course = (Course) obj;
                System.out.println("Course: " + course);
            } else {
                System.out.println("Unknown object type: " + obj);
            }
        }
    }
}
