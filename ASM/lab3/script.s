; STACK SEGMENT
STSEG SEGMENT PARA STACK "STACK"
	DB 64 DUP (0)
STSEG ENDS

; DATA SEGMENT
DSEG SEGMENT PARA PUBLIC "DATA"
	xStr db "Enter x : $"
	yStr db "Enter y : $"
	overflowError db "Overflow error", 10, "$"
	xBuffer db 6, ?, 6 dup ('?')
	yBuffer db 6, ?, 6 dup ('?')
	x dw ?
	y dw ?
	was_overflow db 0
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
		je main_exit
		mov x, ax

		lea bx, yBuffer + 2
		call far ptr ATOI
		cmp was_overflow, 1
		je main_exit
		mov y, ax

		cmp y, 0
		jg maybe_first
		jl second
		je third

		maybe_first:
			cmp x, 5
			je first
			jmp main_exit

		first:
			call far ptr FIRST_ACTION
			jmp writing_output

		second:
			neg y
			call far ptr SECOND_ACTION
			cmp was_overflow, 1
			je main_exit
			jmp writing_output
		third:
			call far ptr THIRD_ACTION
			cmp was_overflow, 1
			je main_exit
			jmp writing_output

		writing_output:
			mov bx, ax
			call far ptr ITOA
		main_exit:
			ret
	MAIN ENDP

	FIRST_ACTION PROC FAR	;( 6 ( x ^ 3 )) / y
		xor ax, ax
		mov ax, 6
		mov bx, x
		xor cx, cx
		mov cx, 3

		loop_label:
			mul bx
			loop loop_label
		mov bx, y
		div bx
		ret
	FIRST_ACTION ENDP

	SECOND_ACTION PROC FAR	;( 38 x ) / ( 5 ( y ^ 2 ))
		mov ax, x
		xor bx, bx
		mov bx, 38
		imul bx
		jo overflow_error
		mov x, ax		;38x
		xor ax, ax
		mov ax, 5
		mov bx, y
		mov cx, 2
		loop_label_second:
			mul bx
			jc overflow_error
			loop loop_label_second
		; ax = 5 ( y ^ 2 )
		mov bx, ax
		mov ax, x
		idiv bx
		jmp exit_second

		overflow_error:
			mov was_overflow, 1
			xor dx, dx
			lea dx, overflowError
			call far ptr WRITING
		
		exit_second:
			ret
	SECOND_ACTION ENDP
	
	THIRD_ACTION PROC FAR ; 25 (x ^ 2)
	xor ax, ax
	mov ax, 25
	mov bx, x
	xor cx, cx
	mov cx, 2
	label_loop_third:
		imul bx
		jo overflow_error_third
		loop label_loop_third
	jmp exit_third

	overflow_error_third:
		mov was_overflow, 1
		xor dx, dx
		lea dx, overflowError
		call far ptr WRITING
	exit_third:
		ret
	THIRD_ACTION ENDP

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