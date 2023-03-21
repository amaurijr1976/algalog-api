package com.algaworks.algalog.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algalog.api.exceptionhandler.Problema.Campo;
import com.algaworks.algalog.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algalog.domain.exception.NegocioException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Problema.Campo> campos = new ArrayList<>();
		ex.getBindingResult().getAllErrors().stream().forEach(error -> {
															String campo = ((FieldError) error).getField();
															campos.add(Campo.builder().nome(campo)
																	.mensagem(messageSource.getMessage(error,LocaleContextHolder.getLocale()))
																	.build());
		
		}); 
												
		
		Problema problema = Problema.builder()
									.status(status.value())
									.dataHora(OffsetDateTime.now())
									.titulo("Um ou mais campos estão preenchidos de forma incorretas, favor avaliar")
									.campos(campos)
									.build();
		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocioException(NegocioException ex, WebRequest req){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Problema problema = Problema.builder()
				.status(status.value())
				.dataHora(OffsetDateTime.now())
				.titulo(ex.getMessage())
				.build();
		return handleExceptionInternal(ex, problema, new HttpHeaders(),status , req);
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleNegocioException(EntidadeNaoEncontradaException ex, WebRequest req){
		HttpStatus status = HttpStatus.NOT_FOUND;
		Problema problema = Problema.builder()
				.status(status.value())
				.dataHora(OffsetDateTime.now())
				.titulo(ex.getMessage())
				.build();
		return handleExceptionInternal(ex, problema, new HttpHeaders(),status , req);
	}
	
	
	
}
