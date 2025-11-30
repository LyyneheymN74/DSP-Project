<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Checkout</title>
    <style>
        .checkout-box {
            max-width: 500px;
            margin: 50px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            text-align: center;
        }
        .price { font-size: 2em; color: green; margin: 20px 0; }
        .btn-confirm {
            background-color: #28a745;
            color: white;
            padding: 15px 30px;
            font-size: 1.1em;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div class="checkout-box">
        <h2>Confirm Your Order</h2>
        <hr>
        
        <h3>Product: ${product.name}</h3>
        <p>${product.description}</p>
        
        <p>Unit Price: $${product.price} x ${quantity} items</p>
        
        <div class="price">Total: $${totalPrice}</div> 
        <form action="${pageContext.request.contextPath}/order/create" method="post">
            <input type="hidden" name="productId" value="${product.id}">
            
            <input type="hidden" name="quantity" value="${quantity}">
            <input type="hidden" name="totalPrice" value="${totalPrice}">
            
            <button type="submit" class="btn-confirm">Confirm Purchase</button>
        </form>
        
        <br>
        <a href="${pageContext.request.contextPath}/home">Cancel</a>
    </div>
</body>
</html>