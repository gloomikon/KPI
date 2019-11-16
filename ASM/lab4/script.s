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

		lea dx, helloStr
		call far ptr WRITING
		lea dx, buffer
		call far ptr READING
		mov ax, 10
		int 29h

		lea bx, buffer + 2
		call far ptr ATOI
		cmp was_overflow, 1
		je foo
		mov n, ax
		cmp n, 1
		jl foo1
		cmp n, 20
		jg foo1
		mov ax, n
		mov i, ax

		reading_loop:
			lea dx, elementStr
			call far ptr WRITING
			lea dx, buffer
			call far ptr READING
			mov ax, 10
			int 29h
			lea bx, buffer + 2
			call far ptr ATOI
			cmp was_overflow, 1
			je foo
			mov bx, n
			sub bx, i
			shl bx, 1
			mov elements[bx], ax
			dec i
			jnz reading_loop
	
		lea dx, arrayStr
		call far ptr WRITING
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
			mov bx, ax
			call far ptr ITOA
			mov ax, ' '
			int 29h
			dec i
			jnz output_array
		mov ax, 10
		int 29h

		lea dx, sumStr
		call far ptr WRITING
		call far ptr ARRAY_SUM
		cmp was_overflow, 1
		je foo4
		mov bx, sum
		call far ptr ITOA
		mov ax, 10
		int 29h

		lea dx, minStr
		call far ptr WRITING
		call far ptr ARRAY_MIN
		mov bx, min
		call far ptr ITOA
		mov ax, 10
		int 29h

		lea dx, sortedStr
		call far ptr WRITING
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
			mov bx, ax
			call far ptr ITOA
			mov ax, ' '
			int 29h
			dec i
			jnz output_array1
		mov ax, 10
		int 29h

		
		; READ X
		lea dx, xStr
		call far ptr WRITING
		lea dx, xBuffer
		call far ptr READING
		mov ax, 10
		int 29h

		; READ Y
		lea dx, yStr
		call far ptr WRITING
		lea dx, yBuffer
		call far ptr READING
		mov ax, 10
		int 29h
	
		lea bx, xBuffer + 2
		call far ptr ATOI
		cmp was_overflow, 1
		je foo3
		mov x, ax

		lea bx, yBuffer + 2
		call far ptr ATOI
		cmp was_overflow, 1
		je foo3
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
				mov ax, '['
				int 29h
				mov bx, i
				call far ptr ITOA
				mov ax, ','
				int 29h 
				mov bx, j
				call far ptr ITOA
				mov ax, ']'
				int 29h
				mov ax, ' '
				int 29h
				lea dx, buffer
				call far ptr READING
				mov ax, 10
				int 29h
				lea bx, buffer + 2
				call far ptr ATOI
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
				jl reading_2d_in
			inc i
			mov ax, i
			cmp ax, y
			jl reading_2d_out
		

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
				mov bx, ax
				call far ptr ITOA
				mov ax, ' '
				int 29h
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

		lea dx, findElemStr
		call far ptr WRITING
		lea dx, buffer
		call far ptr READING
		lea bx, buffer + 2
		call far ptr ATOI
		cmp was_overflow, 1
		je end_main
		mov find, ax

		call far ptr FIND_ELEM


		jmp end_main
		error_main:
			lea dx, numberOfElementsError
			call far ptr WRITING
		end_main:
			ret
	MAIN ENDP
		
	FIND_ELEM PROC FAR
		mov ax, 10
		int 29h
		mov i, 0
		find_2d_out:
			mov j, 0
			find_2d_in:
				mov ax, i
				mov bx, x
				mul bx
				add ax, j
				mov bx, ax
				shl bx, 1
				mov ax, twoDArr[bx]
				cmp ax, find
				jne find_skip
				mov ax, '['
				int 29h
				mov bx, i
				call far ptr ITOA
				mov ax, ','
				int 29h
				mov bx, j
				call far ptr ITOA
				mov ax, ']'
				int 29h
				mov ax, 10
				int 29h
				find_skip:
					inc j
					mov ax, j
					cmp ax, x
					jl find_2d_in
			inc i
			mov ax, i
			cmp ax, y
			jl find_2d_out
		ret
	ENDP FIND_ELEM

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
			lea dx, overflowError
			call far ptr WRITING
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

	ITOA PROC FAR
		or bx, bx
		jns positive_number
		xor ax, ax
		mov al, '-'
		int 29h
		neg bx
		positive_number:
			mov ax, bx
			xor cx, cx	; chars number
			mov bx, 10	; diviator
			itoa_loop:
				xor dx, dx	; remainder stores here
				div bx
				add dl, '0'
				push dx
				inc cx
				test ax, ax
				jnz itoa_loop
			output:
				pop ax
				int 29h
				loop output
			ret
		ITOA ENDP

	ATOI PROC FAR
		xor ax, ax 		; result
		xor cx, cx		; char
		xor di, di		; 10
		mov di, 10
		xor si, si		; sign

		skip_whitespaces:
			mov cl, BYTE PTR [bx]
			cmp cl, 32	; ' '
			je step
			cmp cl , 9	; '\t'
			je step
			jmp sign_check
		step:
			inc bx
			jmp skip_whitespaces
		sign_check:
			cmp cl, 45	; '-'
			je minus
			cmp cl, 43	; '+'
			je plus
			jmp atoi_loop
		minus:
			mov si, 1
			plus:
				inc bx
				jmp atoi_loop
		atoi_loop:
			mov cl, BYTE PTR [bx]
			cmp cl, 48	; '0'
			jl atoi_end
			cmp cl, 57	; '9'
			jg atoi_end
			imul di
			jo error
			sub cl, 48
			add ax, cx
			jo error
			inc bx
			jmp atoi_loop
		atoi_end:
			cmp si, 1
			je make_neg
			jmp exit_atoi
		make_neg:
			neg ax
			jmp exit_atoi
		error:
			mov was_overflow, 1			
			xor dx, dx
			lea dx, overflowError
			call far ptr WRITING
		exit_atoi:
			ret
	ATOI ENDP

	READING PROC FAR
		mov ah, 10
		int 21h
		ret
	READING ENDP

	WRITING PROC FAR
		mov ah, 9
		int 21h
		ret
	WRITING ENDP
CSEG ENDS
END MAIN