FIND_ELEM MACRO x, y, array, elem
    LOCAL find_2d_out, find_2d_in, find_skip, find_2d_in_exit, find_2d_out_exit
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
			mov ax, array[bx]
			cmp ax, elem
			jne find_skip
            PRINT_CHAR '['
            ITOA i
            PRINT_CHAR ','
            ITOA j
            PRINT_CHAR ']'
            PRINT_CHAR 10
			find_skip:
				inc j
				mov ax, j
				cmp ax, x
				jnl find_2d_in_exit
                jmp find_2d_in
        find_2d_in_exit:
		inc i
		mov ax, i
		cmp ax, y
		jnl find_2d_out_exit
        jmp find_2d_out
     find_2d_out_exit:
ENDM