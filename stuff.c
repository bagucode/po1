#include "stdio.h"

#ifdef _WIN32
#define WINDOWS
#include <Windows.h>

typedef __int8           I8;
typedef unsigned __int8  U8;
typedef __int16          I16;
typedef unsigned __int16 U16;
typedef __int32          I32;
typedef unsigned __int32 U32;
typedef __int64          I64;
typedef unsigned __int64 U64;
typedef float            F32;
typedef double           F64;

typedef U8 Bool;
#define True 1
#define False 0

typedef I32 Char;

#ifdef _WIN64
#define OCT64
typedef I64 Word;
typedef U64 Uword;
#else
#define OCT32
typedef I32 Word;
typedef U32 Uword;
#endif

#ifdef _DEBUG
#define DEBUG
#endif

#elif defined (__APPLE__)

#define MACOSX

#include <inttypes.h>

typedef int8_t   I8;
typedef uint8_t  U8;
typedef int16_t  I16;
typedef uint16_t U16;
typedef int32_t  I32;
typedef uint32_t U32;
typedef int64_t  I64;
typedef uint64_t U64;
typedef float    F32;
typedef double   F64;

typedef U8 Bool;
#define True 1
#define False 0

typedef I32 Char;

#ifdef __LP64__
#define OCT64
typedef I64 Word;
typedef U64 Uword;
#else
#define OCT32
typedef I32 Word;
typedef U32 Uword;
#endif

#ifndef NDEBUG
#define DEBUG
#endif

#else

#include <inttypes.h>

typedef int8_t   I8;
typedef uint8_t  U8;
typedef int16_t  I16;
typedef uint16_t U16;
typedef int32_t  I32;
typedef uint32_t U32;
typedef int64_t  I64;
typedef uint64_t U64;
typedef float    F32;
typedef double   F64;

typedef U8 Bool;
#define True 1
#define False 0

typedef I32 Char;

#ifdef __LP64__
#define OCT64
typedef I64 Word;
typedef U64 Uword;
#else
#define OCT32
typedef I32 Word;
typedef U32 Uword;
#endif

#ifndef NDEBUG
#define DEBUG
#endif

#endif

typedef struct _U8Array {
  Uword size;
  U8 data[];
}* U8ArrayRef;

typedef struct _String {
  Uword length;
  U8ArrayRef utf8Data;
}* StringRef;

typedef struct _Type* TypeRef;

typedef struct _VTable {
  TypeRef type;
  void* functions[];
}* VTableRef;

typedef struct _Object {
  void* instance;
  VTableRef vtable;
} Object;

typedef struct _NSBinding {
  StringRef name;
  Object value;
} NSBinding;

typedef struct _NSBindingArray {
  Uword size;
  NSBinding data[];
}* NSBindingArrayRef;

typedef struct _Namespace {
  StringRef name;
  NSBindingArrayRef bindings;
}* NamespaceRef;



int main(int argc, char** argv) {
  printf("Size of I8 is: %d\n", sizeof(I8));
  printf("Size of U8 is: %d\n", sizeof(U8));
  printf("Size of I16 is: %d\n", sizeof(I16));
  printf("Size of U16 is: %d\n", sizeof(U16));
  printf("Size of I32 is: %d\n", sizeof(I32));
  printf("Size of U32 is: %d\n", sizeof(U32));
  printf("Size of I64 is: %d\n", sizeof(I64));
  printf("Size of U64 is: %d\n", sizeof(U64));
  printf("Size of F32 is: %d\n", sizeof(F32));
  printf("Size of F64 is: %d\n", sizeof(F64));
  printf("Size of Word is: %d\n", sizeof(Word));
  printf("Size of Uword is: %d\n", sizeof(Uword));
  printf("Size of _Namespace is: %d\n", sizeof(struct _Namespace));
  printf("Size of NamespaceRef is: %d\n", sizeof(NamespaceRef));
  printf("Size of Char is: %d\n", sizeof(Char));
  printf("Size of Bool is: %d\n", sizeof(Bool));

#ifdef OCT64
  printf("Build is 64 bit ");
#ifdef DEBUG
  printf("debug mode.\n");
#else
  printf("release mode.\n");
#endif
#else
  printf("Build is 32 bit ");
#ifdef DEBUG
  printf("debug mode.\n");
#else
  printf("release mode.\n");
#endif
#endif
  return 0;
}
