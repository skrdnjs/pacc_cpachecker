# 1 "funcpnt2.c"
# 1 "<built-in>"
# 1 "<command-line>"
# 1 "funcpnt2.c"


typedef int (*f)(int,int);

int test(int a, int b){
  return a-b;

}

int main(){
  f pnt[2];
  f* p;
  pnt[0] = test;
  pnt[0](3,4);
  p = &pnt[0];
  (**p)(3,4);

}
