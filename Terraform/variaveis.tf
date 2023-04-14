variable "region" {
  type        = string
  default     = "us-east-1"
  description = "Zona de Disponibilidade em que o recurso será criado na AWS"
}

variable "imageId" {
  type        = string
  default     = "ami-007855ac798b5175e"
  description = "Imagem que será utilizada durante a criação de instancias EC2"
}

variable "instanceType" {
  type        = string
  default     = "t2.micro"
  description = "Tipo de máquina EC2 que sera criada na AWS"
}