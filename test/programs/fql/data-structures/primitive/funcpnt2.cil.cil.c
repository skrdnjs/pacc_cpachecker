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
  int (*__cil_tmp7)(int  , int  ) ;
  unsigned int __cil_tmp8 ;
  unsigned int __cil_tmp9 ;
  int (*__cil_tmp10)(int  , int  ) ;
  int (*__cil_tmp11)(int  , int  ) ;
  unsigned int __cil_tmp12 ;
  unsigned int __cil_tmp13 ;
  f *__cil_tmp14 ;
  unsigned int __cil_tmp15 ;
  f *__cil_tmp16 ;
  unsigned int __cil_tmp17 ;
  unsigned int __cil_tmp18 ;
  f *__cil_tmp19 ;
  unsigned int __cil_tmp20 ;
  f *__cil_tmp21 ;
  unsigned int __cil_tmp22 ;
  unsigned int __cil_tmp23 ;
  f *__cil_tmp24 ;
  unsigned int __cil_tmp25 ;
  f *__cil_tmp26 ;

  {
#line 13
  __cil_tmp3 = 0U;
#line 13
  __cil_tmp12 = 0 * 4U;
#line 13
  __cil_tmp13 = (unsigned int )(pnt) + __cil_tmp12;
#line 13
  __cil_tmp14 = (f *)__cil_tmp13;
#line 13
  __cil_tmp15 = (unsigned int )__cil_tmp14;
#line 13
  __cil_tmp4 = __cil_tmp15 + __cil_tmp3;
#line 13
  __cil_tmp16 = (f *)__cil_tmp4;
#line 13
  *__cil_tmp16 = & test;
#line 14
  __cil_tmp5 = 0U;
#line 14
  __cil_tmp17 = 0 * 4U;
#line 14
  __cil_tmp18 = (unsigned int )(pnt) + __cil_tmp17;
#line 14
  __cil_tmp19 = (f *)__cil_tmp18;
#line 14
  __cil_tmp20 = (unsigned int )__cil_tmp19;
#line 14
  __cil_tmp6 = __cil_tmp20 + __cil_tmp5;
#line 14
  __cil_tmp21 = (f *)__cil_tmp6;
#line 14
  __cil_tmp7 = *__cil_tmp21;
#line 14
  (*__cil_tmp7)(3, 4);
#line 15
  __cil_tmp8 = 0U;
#line 15
  __cil_tmp22 = 0 * 4U;
#line 15
  __cil_tmp23 = (unsigned int )(pnt) + __cil_tmp22;
#line 15
  __cil_tmp24 = (f *)__cil_tmp23;
#line 15
  __cil_tmp25 = (unsigned int )__cil_tmp24;
#line 15
  __cil_tmp9 = __cil_tmp25 + __cil_tmp8;
#line 15
  __cil_tmp26 = (f *)__cil_tmp9;
#line 15
  __cil_tmp10 = *__cil_tmp26;
#line 15
  p = (f *)__cil_tmp10;
#line 16
  __cil_tmp11 = *p;
#line 16
  (*__cil_tmp11)(3, 4);
#line 18
  return (0);
}
}
