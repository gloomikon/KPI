include m_basic.s


; STACK SEGMENT
STSEG SEGMENT PARA STACK "STACK"
	DB 64 DUP (0)
STSEG ENDS

; DATA SEGMENT
DSEG SEGMENT PARA PUBLIC "DATA"
	startStr db "Enter a number : $"
	overflowError db "Number is too big", 10, "$"
	bufferSize DB 16		; 15 chars + RETURN
	inputLength DB 0		; number of read chars
	buffer DB 16 DUP('?')	; actual buffer
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

		WRITING startStr
		READING bufferSize


		ATOI bufferSize
		cmp was_overflow, 1
		je end_label

		add ax, 15
		jo error
		ITOA ax
		jmp end_label
		error:
			WRITING overflowError
		end_label:
			ret
	MAIN ENDP
CSEG ENDS
END MAIN