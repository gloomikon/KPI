; STACK SEGMENT
STSEG SEGMENT PARA STACK "STACK"
	DB 64 DUP (0)
STSEG ENDS

; DATA SEGMENT
DSEG SEGMENT PARA PUBLIC "DATA"
	startStr db "Enter a number : $"
	inputError db "Number has incorrect chars.", 10, "$"
	buffError db "Number is too big", 10, "$"
	bufferSize DB 16		; 15 chars + RETURN
	inputLength DB 0		; number of read chars
	buffer DB 16 DUP('?')	; actual buffer
DSEG ENDS

; CODE SEGMENT

CSEG SEGMENT PARA PUBLIC "CODE"
	
	MAIN PROC FAR
		ASSUME cs: CSEG, ds: DSEG, ss:STSEG
		mov ax, DSEG
		mov ds, ax
		;lea dx, startStr
		;call WRITING
		;call READING

		xor ax, ax
		xor dx, dx
		mov ah, 2
		mov dl, 56
		int 21h


		xor dx, dx
		xor ax, ax
		lea dx, bufferSize
		mov ah, 10
		int 21h

		xor ax, ax
		xor dx, dx
		mov ah, 2
		mov dl, 56
		int 21h
		; call ATOI
		; add ax, 15
		; call ITOA
		ret
	MAIN ENDP
	
	ITOA PROC FAR
		or bx, bx
		jns positive_number
		mov al, '-'
		int 29h
		neg bx
		positive_number:
			mov ax, bx
			xor cx, cx	; chars number
			mov bx, 10	; diviator
			itoa_loop:
				xor dx, dx	; result of division is stored here
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
		xor bx, bx		; iterator
		lea bx, buffer
		xor cx, cx		; cl - current char, ch - step
		xor si, si		; 10
		xor di, di
		mov si, 10

		mov ah, '*'
		int 29h
		xor ax, ax
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
			mov di, 1
			plus:
				inc bx
				jmp atoi_loop
		atoi_loop:
			mov cl, BYTE PTR [bx]
			cmp cl, 48	; '0'
			jl atoi_end
			cmp cl, 57	; '9'
			jg atoi_end
			imul si
			; check for overflow ???
			sub cl, 48
			add ax, cx
			inc bx
			jmp atoi_loop
		atoi_end:
			cmp di, 1
			je make_neg
			jmp exit_atoi
		make_neg:
			neg ax
			jmp exit_atoi
		error:
			xor dx, dx
			lea dx, buffError
			call WRITING
		exit_atoi:
			ret
				
	READING PROC FAR
		lea dx, bufferSize
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
		