WRITING MACRO string
	push ax
	lea dx, string
	mov ah, 9
	int 21h
	pop ax
ENDM

PRINT_CHAR MACRO chr
	push ax
	mov ax, chr
	int 29h
	pop ax
ENDM

READING MACRO buffer
	push ax
	lea dx, buffer
	mov ah, 10
	int 21h
	pop ax
ENDM

ATOI MACRO buffer
	LOCAL @@end_label
	LOCAL @@error
	LOCAL @@loop_label

	lea bx, buffer + 2
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
		jmp @@loop_label
	minus:
		mov si, 1
		plus:
			inc bx
			jmp @@loop_label
	@@loop_label:
		mov cl, BYTE PTR [bx]
		cmp cl, 48	; '0'
		jl atoi_end
		cmp cl, 57	; '9'
		jg atoi_end
		imul di
		jo @@error
		sub cl, 48
		add ax, cx
		jo @@error
		inc bx
		jmp @@loop_label
	atoi_end:
		cmp si, 1
		je make_neg
		jmp @@end_label
	make_neg:
		neg ax
		jmp @@end_label
	@@error:
		mov was_overflow, 1	
		WRITING overflowError
	@@end_label:
ENDM

ITOA MACRO number
	LOCAL @@loop_label
	mov bx, number
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
	@@loop_label:
		xor dx, dx	; remainder stores here
		div bx
		add dl, '0'
		push dx
		inc cx
		test ax, ax
		jnz @@loop_label
	output:
		pop ax
		int 29h
		loop output
ENDM