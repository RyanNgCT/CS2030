class A{
    private final int x;

    A (int x){
        this.x = x; // setting the value
    }

    @Override
    public boolean equals(Object obj){
        if (obj == this){ // same object
            return true;
        }
        if (obj instanceof A a){ // compare the values of properties inside the object
            return this.x == a.x;
        }
        return false;
    }
}