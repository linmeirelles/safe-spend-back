package com.sistema.gastos.exception

class ResourceNotFoundException(message: String) : RuntimeException(message)

class UnauthorizedException(message: String) : RuntimeException(message)

class BusinessException(message: String) : RuntimeException(message)
