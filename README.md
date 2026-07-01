# NovaFiscal 🧾⚙️

O **NovaFiscal** é um motor genérico, modular e escalável de processamento de compras e geração de documentos fiscais (PDF/recibos).  
O objetivo do sistema é atuar como um **core engine desacoplado**, capaz de evoluir para uma arquitetura de microsserviços sem reescrita estrutural.

---

## 🎯 Propósito do Sistema

O NovaFiscal foi projetado para resolver um problema comum em sistemas fiscais modernos:

> Processamento confiável, auditável e extensível de compras, com geração de documentos fiscais e histórico imutável.

Ele funciona como um **pipeline de processamento assíncrono e rastreável**, onde cada compra passa por etapas como:

- Ingestão do pedido
- Validação e enriquecimento
- Persistência estruturada e não estruturada
- Geração de documento fiscal (PDF)
- Histórico completo de auditoria

---

## 🧠 Arquitetura: Por que Poliglota?

O sistema utiliza uma abordagem de **persistência poliglota**:

### 🐘 PostgreSQL (dados estruturados)
Responsável por:
- Compras processadas
- Clientes
- Status de processamento
- Relacionamentos transacionais

**Por quê?**  
Porque precisamos de consistência forte, transações ACID e integridade relacional.

---

### 🍃 MongoDB (dados flexíveis / audit log)
Responsável por:
- Payload bruto das requisições
- Histórico de eventos do processamento
- Snapshots de compras
- Estrutura evolutiva sem migração rígida

**Por quê?**  
Porque o formato do payload pode mudar com o tempo sem quebrar o sistema.

---

## 🧱 Arquitetura Evolutiva para Microsserviços

O projeto nasce como um **monólito modular desacoplado**, preparado para extração futura em microsserviços.

### Módulos conceituais (futuro):

- `ingestion-service` → entrada de compras
- `processing-service` → regras de negócio
- `fiscal-service` → geração de documentos fiscais
- `audit-service` → rastreabilidade e histórico

### Estratégia de evolução:
- Código organizado por domínio (não por camada apenas)
- Baixo acoplamento entre services
- Comunicação futura via eventos (Kafka)

---

## 📡 Mensageria (Fase futura)

O sistema será evoluído para arquitetura orientada a eventos com **Apache Kafka**:

Eventos planejados:

- `PurchaseCreated`
- `PurchaseValidated`
- `FiscalDocumentGenerated`
- `ProcessingFailed`

**Benefício:** processamento assíncrono, escalabilidade horizontal e resiliência.

---

## ⚡ Cache & Idempotência (Redis)

O Redis será introduzido para:

- Evitar processamento duplicado (idempotência)
- Cache de consultas frequentes
- Controle de estado temporário de processamento

---

## 🧪 Estratégia de Testes

O projeto adota cultura **test-first estruturada**:

### Tipos de testes:

#### ✔ Unitários (JUnit 5 + Mockito)
- Testam regras de negócio isoladas
- Sem dependência de Spring ou banco

#### ✔ Integração
- Testam API + contexto Spring Boot
- Validação de fluxo completo

#### ✔ Futuro (Testcontainers)
- PostgreSQL e Mongo reais em container Docker
- Garantia de consistência em CI/CD

---

## 🚀 CI/CD (GitHub Actions)

Pipeline futuro incluirá:

- Build Maven
- Execução de testes
- Análise estática (Checkstyle/SpotBugs)
- Build de imagem Docker
- Deploy automatizado

---

## 🏗️ Stack Tecnológica

- Java 17+
- Spring Boot (Web)
- PostgreSQL
- MongoDB
- Thymeleaf
- iText / Flying Saucer (PDF)
- JUnit 5
- Mockito
- Docker (futuro)
- Kafka (futuro)
- Redis (futuro)

---

## 🧭 Visão do Projeto

O NovaFiscal não é apenas um sistema de emissão de notas.

Ele é um **motor extensível de processamento fiscal e auditoria de transações**, desenhado para:

- Escalabilidade horizontal
- Evolução para microsserviços
- Alta rastreabilidade
- Extensibilidade de regras fiscais

---