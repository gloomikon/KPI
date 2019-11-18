include m_basic.s
include m_funcs.s
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
	result dw ?
	remainder dw ?
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
		WRITING xStr
		READING xBuffer

		; READ Y
		WRITING yStr
		READING yBuffer

		ATOI xBuffer
		cmp was_overflow, 1
		jne xGood
		EXIT_PROGRAM
		xGood:
		mov x, ax

		ATOI yBuffer
		cmp was_overflow, 1
		jne yGood
		EXIT_PROGRAM
		yGood:
		mov y, ax

		cmp y, 0
		jg maybe_first
		jl second
		je foo
		foo: jmp third

		maybe_first:
			cmp x, 5
			je first
			jmp main_exit

		first:
			FIRST_ACTION x, y, remainder, result
			jmp writing_output

		second:
			neg y
			SECOND_ACTION x, y, remainder, result
			cmp was_overflow, 1
			jne second_ok
			EXIT_PROGRAM
			second_ok:
			jmp writing_output
		third:
			THIRD_ACTION x, y, remainder, result
			cmp was_overflow, 1
			je main_exit
			jmp writing_output

		writing_output:
			ITOA result
			PRINT_CHAR 10
			ITOA remainder
		main_exit:
			ret
	MAIN ENDP
CSEG ENDS
END MAIN