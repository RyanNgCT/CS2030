abstract class Func {
    abstract int apply(int x);

    // we need to have f.compose(g)
    Func compose(Func other){
        // return outcome of the composition
        return new Func(){
            int apply(int x){
                int res = other.apply(x);
                return Func.this.apply(res);
            }
        }
    }
}

// Func f = new Func(){
//     int apply(int x){
//         return x + 2;
//     }
// }


// Func g = new Func(){
//     int apply(int x){
//         return x * 2;
//     }
// }