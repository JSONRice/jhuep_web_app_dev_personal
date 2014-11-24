<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <title>Computer Order Form</title>
   <link href="css/mill.css" rel="stylesheet" type="text/css" />
   <link href="css/oform.css" rel="stylesheet" type="text/css" />
   <link href="css/hw2.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="heading">
		<img src="images/mclogo.jpg" alt="Millennium Computers" />
	</div>
	<div id="links">
		<ul>
			<li><a href="#">Home</a></li>
			<li><a href="#">Desktops</a></li>
			<li><a href="#">Laptops</a></li>
			<li><a href="#">Peripherals</a></li>
			<li><a href="#">Support</a></li>
			<li><a href="#">Your Account</a></li>
			<li><a href="#">Shopping Cart</a></li>
			<li><a href="#">Contact Us</a></li>
		</ul>
	</div>
	<div id="pageContent">
		<br>
		<h1>Build Your Computer</h1>
		<br>
		<!-- So far no action as of hw2. method default set to a post. -->
		<form name="order" id="cconfig" action="" method="post">
			<fieldset>
				<legend>Contact Information</legend>
				<label for="fname">First name</label> <input type="text"
					name="fname" autofocus required><br> <label
					for="lname">Last name</label> <input type="text" name="lname"
					required><br> <label for="street">Street</label> <input
					type="text" name="Street" required><br> <label
					for="city">City</label> <input type="text" name="city" required><br>
				<label for="state">State</label> <input type="text" name="state"
					required><br> <label for="zip">ZIP</label> <input
					type="text" name="zip" required pattern="\d{5}([\-]\d{4})?"
					title="Format must be: 99999 or 99999-9999"><br> <label
					for="phone">Phone</label> <input type="tel" name="phone" required
					pattern="\d{3}[\-]\d{3}[\-]\d{4}"
					title="Format must be: 999-999-9999"><br>
			</fieldset>
			<fieldset>
				<legend>Computer Configuration</legend>
				<br> <label for="cpu">Processor Speed</label> <select
					name="cpu">
					<option value="2.4">2.4 GHz</option>
					<option value="3.2">3.2 GHz</option>
					<option value="4.0">4.0 GHz</option>
					<option value="5.5">5.5 GHz</option>
				</select><br> <label for="ram">Memory</label> <select name="ram">
					<option value="1">1 GB</option>
					<option value="2">2 GB</option>
					<option value="4">4 GB</option>
					<option value="8">8 GB</option>
					<option value="12">12 GB</option>
				</select><br> <label for="mon">Monitor</label> <select name="mon">
					<option value="15">15"</option>
					<option value="17">17"</option>
					<option value="19">19"</option>
					<option value="21">21"</option>
				</select><br> <label for="hdrive">Hard Drive</label> <select
					name="hdrive">
					<option value="240">240 GB</option>
					<option value="500">500 GB</option>
					<option value="750">750 GB</option>
					<option value="1000">1 TB</option>
				</select><br> <label style="width: 11.5em" for="dvd">DVD Burner</label>
				<input type="radio" name="dvd" value="1">Yes <input
					type="radio" name="dvd" value="0">No<br> <label
					style="width: 11.5em" for="lan">LAN Card</label> <input
					type="radio" name="lan" value="1">Yes <input type="radio"
					name="lan" value="0">No<br> <label
					style="width: 11.5em" for="mcard">Media Card Reader</label> <input
					type="radio" class="radioleft" name="mcard" value="1">Yes <input
					type="radio" name="mcard" value="0">No<br>
			</fieldset>
			<div id="additionaloptions">
				<input style="float: left" type="checkbox" name="warranty"
					value="warranty">Yes, I want the 24-month extended warranty<br>
				<br> Any special requests on your order?<br>
				<textarea rows="10" cols="60"></textarea>
				<br> <br> <input type="button" value="Send Order">
				<input type="button" value="Cancel Order"> <input
					type="button" value="Contact Me">
			</div>
			<br /> <br /> <br />
			<div id="footer">
				<hr />
				<p align="center">Millennium Computer &#8226; 100 Commerce Drive
					&#8226; Madison, WI 53597 &#8226; (608) 555 - 8441</p>
			</div>
		</form>
	</div>
</body>
</html>