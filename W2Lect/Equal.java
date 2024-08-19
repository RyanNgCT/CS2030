public class Equal {
  public static void compare(int a, int b) {
    if (a == b) {
      System.out.println("a and b are equal");
    }
    else {
      System.out.println("a and b are not equal");
    }
  }
  public static void main(String[] args) {
    int a = 1000;
    int b = 1000;
    compare(a,b);
  }
}