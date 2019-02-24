#ifndef MPOOL_H
#define MPOOL_H
#include <iostream>
#include "image.h"

class mPool {
private:
    struct Byte {
        void *pointer;
        bool usedStatus;
    };
    unsigned int quantity;
    unsigned int length;
    void *memory;
    Byte *bytes;
public:
    mPool(unsigned int, unsigned int);
    void *Allocate();
    void Free(void*);
    ~mPool();
};

Picture* pool_new(mPool*);
void pool_delete(mPool*, Picture*);

#endif // MPOOL_H
