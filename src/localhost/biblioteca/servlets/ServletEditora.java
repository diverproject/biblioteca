package localhost.biblioteca.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import localhost.biblioteca.dao.EditoraDAO;
import localhost.biblioteca.entidades.Editora;

public class ServletEditora implements ServletPost
{
	@Override
	public void post(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String cmd = request.getParameter("cmd");

		Editora editora = new Editora(request);
		EditoraDAO dao = new EditoraDAO(request);

		if (cmd.equals("Buscar por ID"))
		{
			request.setAttribute("editora", dao.selecionar(editora.getId()));
			request.getRequestDispatcher("index.jsp?page=editora&menu=alterar").forward(request, response);
		}

		else if (cmd.equals("Buscar por Nome"))
		{
			request.setAttribute("editora", dao.selecionar(editora.getNome()));
			request.getRequestDispatcher("index.jsp?page=editora&menu=alterar").forward(request, response);
		}

		else if (cmd.equals("Adicionar"))
		{
			if (dao.inserir(editora))
				request.setAttribute("success", String.format("editora '%s' adicionada com êxito", editora.getNome()));
			else
				request.setAttribute("info", String.format("falha ao adicionar editora '%s'", editora.getNome()));

			request.getRequestDispatcher("index.jsp?page=editora").forward(request, response);
		}

		else if (cmd.equals("Salvar"))
		{
			if (dao.atualizar(editora))
				request.setAttribute("success", String.format("editora '%s' salva com êxito", editora.getNome()));
			else
				request.setAttribute("info", String.format("falha ao salvar editora '%s'", editora.getNome()));

			request.getRequestDispatcher("index.jsp?page=editora").forward(request, response);
		}

		else if (cmd.equals("Voltar"))
			request.getRequestDispatcher("index.jsp").forward(request, response);

		else
		{
			request.setAttribute("error", "comando inválido");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}
}
