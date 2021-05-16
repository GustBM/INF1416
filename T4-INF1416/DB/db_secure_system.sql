-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 16, 2021 at 02:31 PM
-- Server version: 5.7.24
-- PHP Version: 7.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_secure_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `grupos`
--

CREATE TABLE `grupos` (
  `g_id` int(11) NOT NULL,
  `g_nome` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `grupos`
--

INSERT INTO `grupos` (`g_id`, `g_nome`) VALUES
(0, 'Usuário Padrão'),
(1, 'Administrador');

-- --------------------------------------------------------

--
-- Table structure for table `mensagem`
--

CREATE TABLE `mensagem` (
  `m_id` int(11) DEFAULT NULL,
  `m_mensagem` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabela de Mensagens de Registro';

--
-- Dumping data for table `mensagem`
--

INSERT INTO `mensagem` (`m_id`, `m_mensagem`) VALUES
(1001, 'Sistema iniciado.'),
(1002, 'Sistema encerrado.'),
(2001, 'Autenticação etapa 1 iniciada.'),
(2002, 'Autenticação etapa 1 encerrada.'),
(2003, 'Login name <login_name> identificado com acesso liberado.'),
(2004, 'Login name <login_name> identificado com acesso bloqueado.'),
(2005, 'Login name <login_name> não identificado.'),
(3001, 'Autenticação etapa 2 iniciada para <login_name>.'),
(3002, 'Autenticação etapa 2 encerrada para <login_name>.'),
(3003, 'Senha pessoal verificada positivamente para <login_name>.'),
(3004, 'Primeiro erro da senha pessoal contabilizado para <login_name>.'),
(3005, 'Segundo erro da senha pessoal contabilizado para <login_name>.'),
(3006, 'Terceiro erro da senha pessoal contabilizado para <login_name>.'),
(3007, 'Acesso do usuario <login_name> bloqueado pela autenticação etapa 2.'),
(4001, 'Autenticação etapa 3 iniciada para <login_name>.'),
(4002, 'Autenticação etapa 3 encerrada para <login_name>.'),
(4003, 'Chave privada verificada positivamente para <login_name>.'),
(4004, 'Chave privada verificada negativamente para <login_name> (caminho inválido).'),
(4005, 'Chave privada verificada negativamente para <login_name> (frase secreta inválida).'),
(4006, 'Chave privada verificada negativamente para <login_name> (assinatura digital inválida).'),
(4007, 'Acesso do usuario <login_name> bloqueado pela autenticação etapa 3.'),
(5001, 'Tela principal apresentada para <login_name>.'),
(5002, 'Opção 1 do menu principal selecionada por <login_name>.'),
(5003, 'Opção 2 do menu principal selecionada por <login_name>.'),
(5004, 'Opção 3 do menu principal selecionada por <login_name>.'),
(5005, 'Opção 4 do menu principal selecionada por <login_name>.'),
(6001, 'Tela de cadastro apresentada para <login_name>.'),
(6002, 'Botão cadastrar pressionado por <login_name>.'),
(6003, 'Senha pessoal inválida fornecida por <login_name>.'),
(6004, 'Caminho do certificado digital inválido fornecido por <login_name>.'),
(6005, 'Confirmação de dados aceita por <login_name>.'),
(6006, 'Confirmação de dados rejeitada por <login_name>.'),
(6007, 'Botão voltar de cadastro para o menu principal pressionado por <login_name>.'),
(7001, 'Tela de alteração da senha pessoal e certificado apresentada para <login_name>.'),
(7002, 'Senha pessoal inválida fornecida por <login_name>.'),
(7003, 'Caminho do certificado digital inválido fornecido por <login_name>.'),
(7004, 'Confirmação de dados aceita por <login_name>.'),
(7005, 'Confirmação de dados rejeitada por <login_name>.'),
(7006, 'Botão voltar de carregamento para o menu principal pressionado por <login_name>.'),
(8001, 'Tela de consulta de arquivos secretos apresentada para <login_name>.'),
(8002, 'Botão voltar de consulta para o menu principal pressionado por <login_name>.'),
(8003, 'Botão Listar de consulta pressionado por <login_name>.'),
(8004, 'Caminho de pasta inválido fornecido por <login_name>.'),
(8005, 'Arquivo de índice decriptado com sucesso para <login_name>.'),
(8006, 'Arquivo de índice verificado (integridade e autenticidade) com sucesso para <login_name>.'),
(8007, 'Falha na decriptação do arquivo de índice para <login_name>.'),
(8008, 'Falha na verificação (integridade e autenticidade) do arquivo de índice para <login_name>.'),
(8009, 'Lista de arquivos presentes no índice apresentada para <login_name>.'),
(8010, 'Arquivo <arq_name> selecionado por <login_name> para decriptação.'),
(8011, 'Acesso permitido ao arquivo <arq_name> para <login_name>.'),
(8012, 'Acesso negado ao arquivo <arq_name> para <login_name>.'),
(8013, 'Arquivo <arq_name> decriptado com sucesso para <login_name>.'),
(8014, 'Arquivo <arq_name> verificado (integridade e autenticidade) com sucesso para <login_name>.'),
(8015, 'Falha na decriptação do arquivo <arq_name> para <login_name>.'),
(8016, 'Falha na verificação (integridade e autenticidade) do arquivo <arq_name> para <login_name>.'),
(9001, 'Tela de saída apresentada para <login_name>.'),
(9002, 'Saída não liberada por falta de one-time password para <login_name>.'),
(9003, 'Botão sair pressionado por <login_name>.'),
(9004, 'Botão voltar de sair para o menu principal pressionado por <login_name>');

-- --------------------------------------------------------

--
-- Table structure for table `registro`
--

CREATE TABLE `registro` (
  `r_id` int(11) NOT NULL,
  `r_idMensagem` int(11) NOT NULL,
  `r_email` text,
  `r_nomeArq` text,
  `r_dataCriacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `usuarios`
--

CREATE TABLE `usuarios` (
  `u_id` int(11) NOT NULL,
  `u_email` varchar(255) NOT NULL,
  `u_nome` varchar(255) NOT NULL,
  `u_senha` varchar(255) NOT NULL,
  `u_grupo` int(1) NOT NULL DEFAULT '0' COMMENT '0-Usuario normal, 1-Administrador',
  `u_salt` varchar(10) NOT NULL,
  `u_bloqueio` date DEFAULT NULL,
  `u_certificado` varchar(255) NOT NULL,
  `u_acessos` int(11) NOT NULL DEFAULT '0',
  `u_leituras` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `usuarios`
--

INSERT INTO `usuarios` (`u_id`, `u_email`, `u_nome`, `u_senha`, `u_grupo`, `u_salt`, `u_bloqueio`, `u_certificado`, `u_acessos`, `u_leituras`) VALUES
(3, 'block@email.com', 'Blocked Blockerson', '', 0, '', '2021-05-14', '', 0, 0),
(4, 'admin@admin.com', 'Admin', '', 1, '', NULL, '', 0, 0),
(6, 'asdf@gmail.com', 'asdfsadfasd', '8b9c04a8af0fc99faccbb064aaf935deaaed4db4', 0, '4bU5xO9a0g', NULL, 'teset', 0, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `grupos`
--
ALTER TABLE `grupos`
  ADD PRIMARY KEY (`g_id`),
  ADD UNIQUE KEY `g_id` (`g_id`);

--
-- Indexes for table `mensagem`
--
ALTER TABLE `mensagem`
  ADD UNIQUE KEY `m_id` (`m_id`),
  ADD KEY `m_id_2` (`m_id`);

--
-- Indexes for table `registro`
--
ALTER TABLE `registro`
  ADD PRIMARY KEY (`r_id`),
  ADD KEY `r_idMensagem` (`r_idMensagem`);

--
-- Indexes for table `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`u_id`),
  ADD UNIQUE KEY `UC_Usuarios` (`u_id`,`u_email`) USING BTREE;

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `registro`
--
ALTER TABLE `registro`
  MODIFY `r_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=482;

--
-- AUTO_INCREMENT for table `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `u_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `registro`
--
ALTER TABLE `registro`
  ADD CONSTRAINT `registro_ibfk_1` FOREIGN KEY (`r_idMensagem`) REFERENCES `mensagem` (`m_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
