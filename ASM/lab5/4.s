include m_basic.s
include m_arrays.s
; STACK SEGMENT
STSEG SEGMENT PARA STACK "STACK"
	DB 64 DUP (0)
STSEG ENDS

; DATA SEGMENT
DSEG SEGMENT PARA PUBLIC "DATA"
	helloStr db "Enter number of elements (1-20) : $"
	elementStr db "Enter an element: $"
	overflowError db "Overflow error", 10, "$"
	numberOfElementsError db "Invalid number!", 10, "$"
	elementError db "String can not be empty"
	findElemStr db "Enter an element to find : $"
	xStr db "Enter X (1-5): $"
	yStr db "Enter Y (1-5): $"
	arrayStr db "Array is : $"
	sumStr db "Sum is : $"
	minStr db "Min is : $"
	sortedStr db "Sorted array is : $"

	buffer db 6, ?, 6 dup ('?')
	xBuffer db 6, ?, 6 dup ('?')
	yBuffer db 6, ?, 6 dup ('?')

	x dw ?
	y dw ?

	elements dw 20 dup (?)
	twoDArr dw 25 dup (?)
	n dw 0
	i dw 0
	j dw 0
	tmp dw 0

	sum dw 0
	min dw ?
	find dw ?

	was_overflow db 0
	was_empty db 0

DSEG ENDS

; CODE SEGMENT
CSEG SEGMENT PARA PUBLIC "CODE"
	
	MAIN PROC FAR
		ASSUME cs: CSEG, ds: DSEG, ss:STSEG

		push ds
		xor ax, ax
		push ax
		mov ax, DSEG
		mov ds, ax

        WRITING helloStr
        READING buffer
        ATOI buffer
		cmp was_overflow, 1
		je boo
		mov n, ax
		cmp n, 1
		jl boo1
		cmp n, 20
		jg boo1
		mov ax, n
		mov i, ax
        jmp reading_loop

        boo: jmp foo
        boo1: jmp foo1

		reading_loop:
            WRITING elementStr
            READING buffer
            ATOI buffer
			cmp was_overflow, 1
			je foo
			mov bx, n
			sub bx, i
			shl bx, 1
			mov elements[bx], ax
			dec i
			jz reading_loop_exit
            jmp reading_loop
        reading_loop_exit:
        WRITING arrayStr
		mov ax, n
		mov i, ax
		jmp output_array
		foo1: jmp error_main
		foo: jmp end_main
		output_array:
			mov bx, n
			sub bx, i
			shl bx, 1
			mov ax, elements[bx]
            ITOA ax
            PRINT_CHAR ' '
			dec i
			jnz output_array
		mov ax, 10
		int 29h

        WRITING sumStr
		call far ptr ARRAY_SUM
		cmp was_overflow, 1
		je foo5
        ITOA sum
        PRINT_CHAR 10

        WRITING minStr
		call far ptr ARRAY_MIN
        ITOA min
		PRINT_CHAR 10
        jmp notfoo5

        foo5: jmp foo4
        notfoo5:

		WRITING sortedStr
		call far ptr ARRAY_SORT
		mov ax, n
		mov i, ax
		jmp output_array1
		foo4: jmp foo3
		output_array1:
			mov bx, n
			sub bx, i
			shl bx, 1
			mov ax, elements[bx]
            ITOA ax
            PRINT_CHAR ' '
			dec i
			jnz output_array1
        PRINT_CHAR 10

		
		; READ X
        WRITING xStr
        READING xBuffer

		; READ Y
		WRITING yStr
        READING yBuffer
	
        ATOI xBuffer
		cmp was_overflow, 1
		je foo6
		mov x, ax

		ATOI yBuffer
		cmp was_overflow, 1
		je foo3
        jmp notfoo6
        foo6: jmp foo3
        notfoo6:
		mov y, ax
		cmp x, 1
		jl foo3
		cmp x, 5
		jg foo3
		cmp y, 1
		jl foo3
		cmp y, 5
		jg foo3

		mov i, 0
		jmp reading_2d_out
		foo3: jmp foo2
		reading_2d_out:
			mov j, 0
			reading_2d_in:
                PRINT_CHAR '['
                ITOA i
                PRINT_CHAR ','
                ITOA j
                PRINT_CHAR ']'
                PRINT_CHAR ' '
                READING buffer
                ATOI buffer
				cmp was_overflow, 1
				je foo2
				mov tmp, ax
				mov ax, i
				mov bx, x
				mul bx
				add ax, j
				mov bx, ax
				shl bx, 1
				mov ax, tmp
				mov twoDArr[bx], ax
				inc j
				mov ax, j
				cmp ax, x
				jnl reading_2d_in_exit
                jmp reading_2d_in
            reading_2d_in_exit:
			inc i
			mov ax, i
			cmp ax, y
			jnl reading_2d_out_exit
            jmp reading_2d_out
		reading_2d_out_exit:

		mov i, 0
		jmp writing_2d_out
		foo2: jmp end_main
		writing_2d_out:
			mov j, 0
			writing_2d_in:
				mov ax, i
				mov bx, x
				mul bx
				add ax, j
				mov bx, ax
				shl bx, 1
				mov ax, twoDArr[bx]
                ITOA ax
                PRINT_CHAR ' '
				inc j
				mov ax, j
				cmp ax, x
				jl writing_2d_in
			mov ax, 10
			int 29h
			inc i
			mov ax, i
			cmp ax, y
			jl writing_2d_out

		WRITING findElemStr
        READING buffer
        ATOI buffer
		cmp was_overflow, 1
		je foo7
		mov find, ax
        jmp find_elem_label
        foo7: jmp end_main
        find_elem_label:
        FIND_ELEM x, y, twoDArr, find

		jmp end_main
		error_main:
            WRITING numberOfElementsError
		end_main:
			ret
	MAIN ENDP
		


	ARRAY_SORT PROC FAR
	out_loop_sort:
		mov ax, i
		mov min, ax
		mov j, ax
		inc j
		mov ax, n
		cmp j, ax
		jge sort_end
		inner_loop_sort:
			mov bx, min
			shl bx, 1
			mov ax, elements[bx]
			mov bx, j
			shl bx, 1
			mov cx, elements[bx]
			cmp ax, cx
			jng skip_update
			mov ax, j
			mov min, ax
			skip_update:
			inc j
			mov ax, j
			cmp ax, n
			jl inner_loop_sort
		mov bx, i
		shl bx, 1
		mov ax, elements[bx]
		mov bx, min
		shl bx, 1
		mov cx, elements[bx]
		mov dx, cx
		mov bx, min
		shl bx, 1
		mov elements[bx], ax
		mov bx, i
		shl bx, 1
		mov elements[bx], dx
		inc i
		mov ax, n
		cmp i, ax
		jl out_loop_sort
		sort_end:
			ret
	ENDP ARRAY_SORT

	ARRAY_SUM PROC FAR
		mov ax, n
		mov i, ax
		sum_loop:
			mov bx, n
			sub bx, i
			shl bx, 1
			mov ax, elements[bx]
			add sum, ax
			jo error_sum
			dec i
			jnz sum_loop
		jmp sum_end
		error_sum:
			mov was_overflow, 1
            WRITING overflowError
		sum_end:
			ret
	ENDP ARRAY_SUM

	ARRAY_MIN PROC FAR
		mov ax, elements[0]
		mov min, ax
		mov ax, n
		mov i, ax
		min_loop:
			mov bx, n
			sub bx, i
			shl bx, 1
			mov ax, elements[bx]
			cmp ax, min
			jg iterate_min
			mov min, ax
			iterate_min: 
				dec i
				jnz min_loop
		ret
	ENDP ARRAY_MIN
CSEG ENDS
END MAIN