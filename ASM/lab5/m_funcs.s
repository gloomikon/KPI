FIRST_ACTION MACRO x, y, remainder, result	;( 6 ( x ^ 3 )) / y
	LOCAL @@loop_label
	xor ax, ax
	mov ax, 6
	mov bx, x
	xor cx, cx
	mov cx, 3

	@@loop_label:
		mul bx
		loop @@loop_label
	mov bx, y
	div bx
	mov remainder, dx
	mov result, ax
ENDM

SECOND_ACTION MACRO  x, y, remainder, result	;( 38 x ) / ( 5 ( y ^ 2 ))
	LOCAL @@loop_label, @@error, @@exit
	mov ax, x
	xor bx, bx
	mov bx, 38
	imul bx
	jo @@error
	mov x, ax		;38x
	xor ax, ax
	mov ax, 5
	mov bx, y
	mov cx, 2
	@@loop_label:
		mul bx
		jc @@error
		loop @@loop_label
	; ax = 5 ( y ^ 2 )
	mov bx, ax
	mov ax, x
	idiv bx
	mov result, ax
	mov remainder, dx
	jmp @@exit
	@@error:
		mov was_overflow, 1
		WRITING overflowError
	@@exit:
ENDM

THIRD_ACTION MACRO x, y, remainder, result ; 25 (x ^ 2)
	LOCAL @@loop_label, @@error, @@exit
	xor ax, ax
	mov ax, 25
	mov bx, x
	xor cx, cx
	mov cx, 2
	@@loop_label:
		imul bx
		jo @@error
		loop @@loop_label
	mov result, ax
	mov remainder, 0
	jmp @@exit

	@@error:
		mov was_overflow, 1
		WRITING overflowError
	@@exit:
ENDM
