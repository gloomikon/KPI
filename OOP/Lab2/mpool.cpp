#include "mpool.h"
#include <iostream>
mPool::mPool(unsigned int sizeOfElement, unsigned int numberOfElements)
{
    quantity = numberOfElements;
    length = sizeOfElement;
    memory = operator new (length * quantity + sizeof(Byte) * quantity);
    bytes = reinterpret_cast<Byte*>(static_cast<char*>(memory) + quantity * length);
    for (unsigned int i = 0; i < quantity; i++)
    {
        Byte byte;
        byte.pointer = reinterpret_cast<void*>(static_cast<char*>(memory) + i * sizeOfElement);
        byte.usedStatus = false;
        bytes[i] = byte;
    }
}

void* mPool::Allocate()
{
    for (unsigned int i = 0; i < quantity; i++)
        if (!bytes[i].usedStatus)
        {
            std::cout << "Memory has been successfully allocated at " << bytes[i].pointer << " :)"<< std::endl;
            bytes[i].usedStatus = true;
            return bytes[i].pointer;
        }
    std::cout << "Failed to allocate memory :(" << std::endl;
    return nullptr;
}

void mPool::Free(void *pointer)
{
    bytes[static_cast<unsigned int>(reinterpret_cast<char*>(pointer) - static_cast<char*>(memory)) / length].usedStatus = false;
    std::cout << "Memory has been freed successfully :)" << std::endl;
}

mPool::~mPool()
{
    operator delete (memory);
}


Picture* pool_new(mPool *pool)
{
    Picture *a = static_cast<Picture*>(pool->Allocate());
    if (a)
    {
        a->pixels = reinterpret_cast<Pixel*>(reinterpret_cast<char*>(a) + sizeof(Picture));
    }
    return a;
}

void pool_delete(mPool *pool, Picture *picture)
{
    pool->Free(static_cast<void*>(picture));
}
