package org.example;

import io.restassured.internal.path.json.mapping.JsonObjectDeserializer;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.json.simple.JSONObject;

import java.util.HashMap;

import com.github.javafaker.Faker;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;

public class apiTesting {
    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImRhN2E1OWQyLWRlYjEtNGE5Yy04MDQ5LTQ1NGE0MDI4NDdhZCIsImlhdCI6MTc0NzA3NTU4OCwiZXhwIjoxNzQ3MTYxOTg4fQ.FT1054nOGPut7UL2VaiHX0xTgD0EQrynVYuF-dvMxec";
    Faker faker = new Faker();

    @BeforeClass
    public void setup() {
        baseURI = "http://localhost:3000";
    }

    @Test
    public void logarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@gmail.com");
        usuario.setSenha("Teste123!");

        given().contentType(ContentType.JSON).body(usuario).when().post("/auth/login").then().statusCode(200).log().all();

    }

    @Test
    public void criarUmNovoUsuario() {
        Usuario usuario = criarUsuario.criarUsuarioValido();
        given().contentType(ContentType.JSON).body(usuario).when().post("/usuarios").then().log().all().statusCode(201);
    }

    @Test
    public void criarUmNovoUsuarioSemNome() {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto("");
        usuario.setNomeUsuario(faker.name().username());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setSenha(faker.internet()
                .password(15, 20,
                        true, true, true));

        given().contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/usuarios").then().log().all().statusCode(400)
                .body(containsString("Nome completo é obrigatório"));
    }

    @Test
    public void criarUmNovoUsuarioSemNomeUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(faker.name().fullName());
        usuario.setNomeUsuario("");
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setSenha(faker.internet()
                .password(15, 20,
                        true, true, true));

        given().contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/usuarios").then().log().all().statusCode(400)
                .body(containsString("Nome de usuário é obrigatório"));

    }

    @Test
    public void criarUmNovoUsuarioSemEmail() {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(faker.name().fullName());
        usuario.setNomeUsuario(faker.name().username());
        usuario.setEmail("");
        usuario.setSenha(faker.internet()
                .password(15, 20,
                        true, true, true));

        given().contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/usuarios").then().log().all().statusCode(400)
                .body(containsString("Email inválido"));

    }

    @Test
    public void criarUmNovoUsuarioEmailFormatadoErrado() {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(faker.name().fullName());
        usuario.setNomeUsuario(faker.name().username());
        usuario.setEmail("testandooteste@@.com");
        usuario.setSenha(faker.internet()
                .password(15, 20,
                        true, true, true));

        given().contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/usuarios").then().log().all().statusCode(400)
                .body(containsString("Email inválido"));

    }

    @Test
    public void criarUmNovoUsuarioSemSenha() {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(faker.name().fullName());
        usuario.setNomeUsuario(faker.name().username());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setSenha("");

        given().contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/usuarios").then().log().all().statusCode(400)
                .body("errors[0].msg", equalTo("Senha deve ter no mínimo 6 caracteres"),
                        "errors[1].msg", equalTo("Senha deve conter pelo menos um número"),
                        "errors[2].msg", equalTo("Senha deve conter pelo menos uma letra maiúscula"));

    }

    @Test
    public void criarUmNovoUsuarioSenhaMenorValor() {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(faker.name().fullName());
        usuario.setNomeUsuario(faker.name().username());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setSenha("Teste");

        given().contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/usuarios").then().log().all().statusCode(400)
                .body(containsString("Senha deve ter no mínimo 6 caracteres"));

    }

    @Test
    public void criarUmNovoUsuarioSenhaSemMaiusculo() {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(faker.name().fullName());
        usuario.setNomeUsuario(faker.name().username());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setSenha("teste1");

        given().contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/usuarios").then().log().all().statusCode(400)
                .body(containsString("Senha deve conter pelo menos uma letra maiúscula"));

    }

    @Test
    public void criarUmNovoUsuarioSenhaSemCaractere() {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(faker.name().fullName());
        usuario.setNomeUsuario(faker.name().username());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setSenha("Testee");

        given().contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/usuarios").then().log().all().statusCode(400)
                .body(containsString("Senha deve conter pelo menos um número"));

    }

    @Test(enabled = false)
    public void criarUmNovoUsuarioNomeComlpetoSemString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nomeCompleto", "111111");
        jsonObject.put("nomeUsuario", faker.name().username());
        jsonObject.put("email", faker.internet().emailAddress());
        jsonObject.put("senha", "Teste123!");

        String payload = jsonObject.toJSONString();

        given().contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/usuarios").then().log().all().statusCode(201);

    }

    @Test(enabled = false)
    public void criarUmNovoUsuarioNomeUsuarioSemString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nomeCompleto", faker.name().fullName());
        jsonObject.put("nomeUsuario", faker.number().digits(6));
        jsonObject.put("email", faker.internet().emailAddress());
        jsonObject.put("senha", "Teste123!");

        String payload = jsonObject.toJSONString();

        given().contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/usuarios").then().log().all().statusCode(201);

    }

    @Test
    public void criarUmNovoUsuarioEmailSemString() {

        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(faker.name().fullName());
        usuario.setNomeUsuario(faker.name().username());
        usuario.setEmail("1111111111111111");
        usuario.setSenha(faker.internet()
                .password(15, 20,
                        true, true, true));

        given().contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/usuarios").then().body("errors[0].msg", equalTo("Email inválido")).log().all().statusCode(400);

    }

    @Test
    public void listarUsuarios() {
        given().contentType(ContentType.JSON).auth().oauth2(token).when()
                .get("/usuarios").then().log().all().statusCode(200).log().all();

    }

    @Test
    public void listarUsuarioNome() {
        given().contentType(ContentType.JSON).queryParam("nomeUsuario", "myles.damore").auth().oauth2(token).when()
                .get("/usuarios").then().body(containsString("myles.damore")).log().all().statusCode(200);

    }

    //Api não retorna Usuário não encontrado.
    @Test
    public void listarUsuarioNomeInexistente() {
        given().contentType(ContentType.JSON).queryParam("nomeUsuario", "123445").auth().oauth2(token).when()
                .get("/usuarios").then().body("", hasSize(0)).log().all().statusCode(200);

    }

    @Test
    public void listarUsuarioEmail() {
        given().contentType(ContentType.JSON).queryParam("email", "teste@gmail.com").auth().oauth2(token).when()
                .get("/usuarios").then().body("email[0]", equalTo("teste@gmail.com")).log().all().statusCode(200);

    }

    //Api não retorna E-mail não encontrado.
    @Test
    public void deveRetornarListaVaziaQuandoEmailNaoExiste() {
        given().contentType(ContentType.JSON).queryParam("email", "notExists@gmail.com").auth().oauth2(token).when()
                .get("/usuarios").then().body("", hasSize(0)).log().all().statusCode(200);

    }

    @Test
    public void listarUsuarioId() {
        String id = "260fb0eb-a4ab-45d8-8304-ad61236500bd";

        given().contentType(ContentType.JSON).auth().oauth2(token).when()
                .get("/usuarios/" + id).then().body("id", equalTo(id)).log().all().statusCode(200);

    }

    @Test
    public void listarUsuarioIdInexistente() {
        String id = "Não existo";

        given().contentType(ContentType.JSON).auth().oauth2(token).when()
                .get("/usuarios/" + id).then().body("erro", equalTo("Usuário não encontrado")).log().all().statusCode(404);

    }

    @Test
    public void listarUsuarioSemToken() {
        String id = "Não existo";
        String tokenJWT = "invalidToken";

        given().contentType(ContentType.JSON).auth().oauth2(tokenJWT).when()
                .get("/usuarios/" + id).then().body("erro", equalTo("Token inválido")).log().all().statusCode(401);

    }

    @Test
    public void atualizarUmUsuario() {
        String id = "6eed49cc-256e-4fbe-96d5-6d79a0003e9b";
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(faker.name().fullName());
        usuario.setNomeUsuario(faker.name().username());
        usuario.setEmail("switchEmail@teste.com");
        usuario.setSenha("Teste123");


        given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(usuario).when().put("/usuarios/" + id).then()
                .body("nomeCompleto", equalTo(usuario.getNomeCompleto()), "nomeUsuario", equalTo(usuario.getNomeUsuario()))
                .log().all().statusCode(200);
    }

    @Test
    public void atualizarUmUsuarioSemToken() {
        String id = "6eed49cc-256e-4fbe-96d5-6d79a0003e9b";
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(faker.name().fullName());
        usuario.setNomeUsuario(faker.name().username());
        usuario.setEmail("switchEmail@teste.com");
        usuario.setSenha("Teste123");


        given().contentType(ContentType.JSON)
                .body(usuario).when().put("/usuarios/" + id).then()
                .body("erro", equalTo("Token não fornecido"))
                .log().all().statusCode(401);
    }

    @Test
    public void nomeDeUsuarioJaEstaEmUso() {
        String id = "ccfbd144-e9bf-402a-bd5a-e26259a13c33";
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(faker.name().fullName());
        usuario.setNomeUsuario("UserTesting");
        usuario.setEmail("switchEmail@teste.com");
        usuario.setSenha("Teste123");


        given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(usuario).when().put("/usuarios/" + id).then()
                .body("erro", equalTo("Nome de usuário já está em uso"))
                .log().all().statusCode(400);
    }

    @Test
    public void excluirUsuario() {

        //Teste Auto Sufuciente, vou criar um usuário e exluir no mesmo teste

        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(faker.name().fullName());
        usuario.setNomeUsuario(faker.name().username());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setSenha(faker.internet()
                .password(15, 20, true, true, true));

        Response response = given().contentType(ContentType.JSON)
                .body(usuario).when().post("/usuarios");

        String id = response.then().statusCode(201).log().all().extract().path("id");

        //Excluir Usuário
        given().contentType(ContentType.JSON)
                .pathParam("id", id)
                .auth().oauth2(token)
                .when().delete("/usuarios/{id}").then()
                .log().all().statusCode(204);

        given().contentType(ContentType.JSON)
                .pathParam("id", id)
                .auth().oauth2(token)
                .when().get("/usuarios/{id}").then()
                .body("erro", equalTo("Usuário não encontrado"))
                .log().all().statusCode(404);
    }

    @Test
    public void excluirUsuarioNaoEncontrado() {

        given().contentType(ContentType.JSON)
                .pathParam("id", "notAnValidId")
                .auth().oauth2(token)
                .when().delete("/usuarios/{id}").then()
                .body("erro", equalTo("Usuário não encontrado"))
                .log().all().statusCode(404);
    }

    @Test
    public void criarCategoriaComSucesso() {

        Categorias categorias = new Categorias();
        categorias.setNome(faker.commerce().productName());
        categorias.setDescricao("Mouse Game");

        given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(categorias)
                .when().post("/categorias").then()
                .body("nome", equalTo(categorias.getNome()), "descricao", equalTo(categorias.getDescricao()))
                .log().all().statusCode(201);
    }

    @Test
    public void criarCategoriaComMesmoNome() {

        Categorias categorias = new Categorias();
        categorias.setNome("Sleek Granite Shoes");
        categorias.setDescricao("Sapato");

        given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(categorias)
                .when().post("/categorias").then()
                .body("erro", equalTo("Nome de categoria já existe"))
                .log().all().statusCode(400);
    }

    @Test
    public void criarCategoriaSemToken() {

        Categorias categorias = new Categorias();
        categorias.setNome("Sleek Granite Shoes");
        categorias.setDescricao("Sapato");

        given().contentType(ContentType.JSON)
                .body(categorias)
                .when().post("/categorias").then()
                .body("erro", equalTo("Token não fornecido"))
                .log().all().statusCode(401);
    }

    @Test(enabled = false)
    public void criarCategoriaDadosInvalidos() {

        Categorias categorias = new Categorias();
        categorias.setNome("My Category");
        categorias.setDescricao("My Description");

        given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body(categorias)
                .when().post("/categorias").then()
                .log().all().statusCode(200);
    }

    @Test
    public void listarCategorias() {

        given()
                .auth().oauth2(token)
                .when().get("/categorias").then()
                .body("id", everyItem(not(anyOf(nullValue(), is("")))))
                .log().all().statusCode(200);
    }

    @Test
    public void listarCategoriasPorID() {

        String id = "683a2773-6f6a-430e-8246-18cd46e66dac";


        given().auth().oauth2(token).pathParam("id", id).when().get("categorias/{id}")
                .then().body("id", equalTo(id)).log().all().statusCode(200);

    }

    @Test
    public void listarCategoriasPorIDinvalido() {

        String id = "Invalid ID";


        given().auth().oauth2(token).pathParam("id", id).when().get("categorias/{id}")
                .then().body("erro", equalTo("Categoria não encontrada")).log().all().statusCode(404);

    }

    @Test
    public void listarCategoriasPorIDSemToken() {

        String id = "683a2773-6f6a-430e-8246-18cd46e66dac";


        given().pathParam("id", id).when().get("categorias/{id}")
                .then().body("erro", equalTo("Token não fornecido")).log().all().statusCode(401);

    }

    @Test
    public void atualizarDescricaoCategoria() {

        Categorias categorias = new Categorias();
        categorias.setNome("Teste");
        categorias.setDescricao("Testando o Endpoit de Atualizar Categoria");
        String id = "abd0c23d-9f31-412a-afc9-3e6dfb26b11a";


        given().auth().oauth2(token).contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(categorias).when().put("categorias/{id}")
                .then()
                .log().body().statusCode(200)
                .body("descricao", equalTo(categorias.getDescricao()));

    }

    @Test
    public void CriarExcluirEVerificarexclusaoDaCategoria() {

        //Criando uma categoria com sucesso
        Categorias categorias = new Categorias();
        categorias.setNome("Criando uma Categoria");
        categorias.setDescricao("Categoria que vai ser excluida");


        Response response = given().auth().oauth2(token).contentType(ContentType.JSON)
                .body(categorias).when().post("categorias");

                String id = response.then()
                .log().body().statusCode(201)
                .body("nome", equalTo(categorias.getNome()), "descricao", equalTo(categorias.getDescricao()))
                        .extract().path("id");

                //Excluir Categoria Criada
                given().auth().oauth2(token).pathParam("id", id).when().delete("categorias/{id}")
                        .then().statusCode(204).log().all();

                //Verificando se a categoria foi excluída
                given().auth().oauth2(token).pathParam("id", id).when().get("categorias/{id}")
                .then().body("erro", equalTo("Categoria não encontrada")).log().all().statusCode(404);
    }

    @Test
    public void ExcluirCategoriaNaoExistente() {

        String id = "ID Não Existente";

        given().auth().oauth2(token).pathParam("id", id).when().delete("categorias/{id}")
                .then().body("erro", equalTo("Categoria não encontrada")).statusCode(404).log().all();
    }

}