/* Generated by CIL v. 1.3.7 */
/* print_CIL_Input is true */

#line 3 "funcpnt2.c"
typedef int (*f)(int  , int  );
#line 5 "funcpnt2.c"
int test(int a , int b ) 
{ 

  {
#line 6
  return (a - b);
}
}
#line 10 "funcpnt2.c"
int main(void) 
{ f pnt[2] ;
  f *p ;
  unsigned int __cil_tmp3 ;
  unsigned int __cil_tmp4 ;
  unsigned int __cil_tmp5 ;
  unsigned int __cil_tmp6 ;
  f __cil_tmp7 ;
  unsigned int __cil_tmp8 ;
  unsigned int __cil_tmp9 ;
  f __cil_tmp10 ;

  {
#line 13
  __cil_tmp3 = 0 * 4U;
#line 13
  __cil_tmp4 = (unsigned int )(pnt) + __cil_tmp3;
#line 13
  *((f *)__cil_tmp4) = & test;
#line 14
  __cil_tmp5 = 0 * 4U;
#line 14
  __cil_tmp6 = (unsigned int )(pnt) + __cil_tmp5;
#line 14
  __cil_tmp7 = *((f *)__cil_tmp6);
#line 14
  (*__cil_tmp7)(3, 4);
#line 15
  __cil_tmp8 = 0 * 4U;
#line 15
  __cil_tmp9 = (unsigned int )(pnt) + __cil_tmp8;
#line 15
  p = (f *)__cil_tmp9;
#line 16
  __cil_tmp10 = *p;
#line 16
  (*__cil_tmp10)(3, 4);
#line 18
  return (0);
}
}
