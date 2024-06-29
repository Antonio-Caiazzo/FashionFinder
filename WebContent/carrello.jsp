<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>FashionFinder - Carrello</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@ include file="./layout/navbar.jsp"%>
	<div class="main-content-container">
		
		<div class="header-cart">
			<%@ include file="./layout/pageTitle.jsp"%>
		</div>

		<ul id="shopping-cart">
			<li class="shopping-item"><img
				src="https://w.wallhaven.cc/full/zy/wallhaven-zyj28v.jpg"
				alt="Articolo 1" />
				<div>
					<h3>Nome Articolo 1</h3>
					<p>Prezzo: €19,99</p>
					<input type="number" value="1" min="1" class="quantity-input" />
					<button class="remove-button">Rimuovi</button>
				</div></li>

			<li class="shopping-item"><img
				src="https://w.wallhaven.cc/full/zy/wallhaven-zyj28v.jpg"
				alt="Articolo 1" />
				<div>
					<h3>Nome Articolo 1</h3>
					<p>Prezzo: €19,99</p>
					<input type="number" value="1" min="1" class="quantity-input" />
					<button class="remove-button">Rimuovi</button>
				</div></li>
		</ul>
		<div class="card-footer">
			<p>
				Totale: <span id="total-price">0</span>
			</p>
		</div>
		<%@ include file="./layout/footer.jsp"%>
	</div>
</body>
</html>