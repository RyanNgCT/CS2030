class A {
    int x;
    A(int x) {
        this.x = x;
    }
}

class B {}

class C {}

class D {}

class E {}

B f(A a) {
    System.out.println("f: start");
    doWork(a.x);
    System.out.println("f: done");
    return new B();
}

C g(B b, int n) {
    System.out.println("g: start");
    doWork(n);
    System.out.println("g: done");
    return new C();
}

D h(B b, int n) {
    System.out.println("h: start");
    doWork(n);
    System.out.println("h: done");
    return new D();
}

E n(C c, D d) {
    System.out.println("n: proceeds");
    return new E();
}

// void foo(int m, int n) {
//     // do f for 5 seconds
//     B b = f(new A(5));

//     // g is done for "m" seconds
//     C c = g(b, m);
//     D d = h(b, n);
//     E e = n(c, d);
// }

void foo(int m, int n) {
    // do f for 5 seconds
    B b = f(new A(5));
    Thread t = new Thread(() -> g(b, m));
    t.start();    // starts the thread

    D d = h(b, n);
    while (t.isAlive()) {
        doWork(1);
    }

    System.out.println("n proceeds");
}

int doWork(int n) {
    try {
        Thread.sleep(n * 1000);    // Thread.sleep throws InterruptedException
    } catch (InterruptedException e) {}
    return n;
}
