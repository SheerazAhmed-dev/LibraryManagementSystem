package library;
import library.datastructures.DynamicArray;

public class DynamicArrayTestFile{
    public static void main(String[] args){
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();

        System.out.println("====== Dynamic Array Testing =====");

        //Test Number 1. Adding elements and resizing the array
        System.out.println("\n1.Add and ReSize");
        for(int i=1; i<=15; i++){
            dynamicArray.add(i);
        }
        dynamicArray.display();

        //Test number 2. The insert method
        System.out.println("\n2.Insert");
        dynamicArray.insert(0, 55);
        dynamicArray.insert(3, 99);
        dynamicArray.insert(dynamicArray.size(), 101);

        dynamicArray.display();

        //Test number 3. The get and Set methods
        System.out.println("\n3. Get and Set");
        System.out.println("element at index 0: " + dynamicArray.get(0));
        int oldValue = dynamicArray.set(0, 9999);

        System.out.println("Old value: " + oldValue);
        System.out.println("New value: " + dynamicArray.get(0));

        //Test number 4. The search method
        System.out.println("\n4. search");
        System.out.println("Index of 3: " + dynamicArray.indexOf(3));
        System.out.println("Contains 9999: " + dynamicArray.contains(9999));
        System.out.println("Contains 100: " + dynamicArray.contains(100));

        //Test number 5. The remove method
        System.out.println("\n5.Remove");
        System.out.println("Removed element: " + dynamicArray.remove(0));
        System.out.println("Removed element: " + dynamicArray.remove(2));
        System.out.println("Removed element: " + dynamicArray.remove(dynamicArray.size()-1));
        dynamicArray.display();

        //Test number 6. The Shrink method
        System.out.println("\n6.Shrinking");
        System.out.println("Initial size before shrinking: " + dynamicArray.size());
        System.out.println("Initial capacity before shrinking: " + dynamicArray.capacity());

        while(dynamicArray.size() > 5){
            dynamicArray.remove(dynamicArray.size()-1);
        }

        System.out.println("Size after shrinking: " + dynamicArray.size());
        System.out.println("Capacity after Shrinking : " + dynamicArray.capacity());

        //Test number 7. invalid index test
        System.out.println("\n7. Invalid index");
        try{
            dynamicArray.get(100);
        }catch(IndexOutOfBoundsException e){
            System.out.println("Invalid index handled successfully!");
        }

        //Test number 8. Null validation Testing
        System.out.println("\n8. Null validation");
        try{
            dynamicArray.add(null);
        }catch(IllegalArgumentException e){
            System.out.println("Null element handled successfully!");
        }

        //Test number 9. The clear method
        System.out.println("\n9. Clear");
        dynamicArray.clear();
        System.out.println("Is empty? : " + dynamicArray.isEmpty());
        System.out.println("Size: " + dynamicArray.size());
        System.out.println("Capacity: " + dynamicArray.capacity());

        //Test number 10. Remove from an empty array test
        System.out.println("\n10. Remove from empty array");
        try{
            dynamicArray.remove(0);
        }catch(IndexOutOfBoundsException e){
            System.out.println("Cannot remove from an empty array!");
        }

        System.out.println("====== Testing completed ======");
    }
}