from flask import *
from src.database import *

app = Flask(__name__)

@app.route('/login',methods=['post'])
def login():
    unam = request.form["user_name"]
    pswd = request.form["password"]
    qry = "SELECT * FROM `login` WHERE  `username`=%s AND `password`=%s"
    val = (unam,pswd)
    print(val)
    res = selectone(qry,val)
    print(res)
    if res is None:
        return jsonify({'task':'invalid'})
    else:
        id = res[0]
        return jsonify({'task':'valid','id':id})

@app.route('/registration',methods=['post'])
def registration():
    try:
            fname = request.form['firstname']
            lname = request.form['lastname']
            email = request.form['email']
            gender = request.form['gender']
            place = request.form['place']
            post = request.form['post']
            pin = request.form['pin']
            district = request.form['district']
            phone = request.form['phone']
            uname = request.form['username']
            pswd = request.form['password']
            qry = "insert into login values(null,%s,%s,'customer')"
            val = (uname,pswd)
            id = iud(qry,val)
            qry = "INSERT INTO `user` VALUES(NULL,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
            val = (id,fname,lname,email,gender,place,post,pin,district,phone)
            iud(qry,val)
            return jsonify({'task':'valid'})
    except Exception as e:
        print(e)
        return jsonify({'task':'failed'})

@app.route('/search_contractor',methods=['post'])
def search_contractor():
    print(request.form)
    service = request.form['service']
    latitude = request.form['latitude']
    longitude = request.form['longitude']
    print(latitude,longitude)
    qry="SELECT `register`.*,`location`.*, (3959 * ACOS ( COS ( RADIANS(%s) ) * COS( RADIANS( `location`.lattitude) ) * COS( RADIANS( `location`.longitude ) - RADIANS(%s) ) + SIN ( RADIANS(%s) ) * SIN( RADIANS( `location`.lattitude ) ))) AS user_distance FROM`location` JOIN `register` ON `location`.user_id=`register`.lid WHERE `register`.service=%s HAVING user_distance < 31.068"
    #qry="SELECT `register`.*,`location`.*, (3959 * ACOS ( COS ( RADIANS(%s) ) * COS( RADIANS( `location`.lattitude) ) * COS( RADIANS( `location`.longitude ) - RADIANS(%s) ) + SIN ( RADIANS(%s) ) * SIN( RADIANS( `location`.lattitude ) ))) AS user_distance FROM`location` JOIN `register` ON `location`.user_id=`register`.lid WHERE `register`.service=%s HAVING user_distance"

    val=(latitude,longitude,latitude,service)
    res = androidselectall(qry,val)
    print(res)
    return jsonify(res)

@app.route('/work',methods=['post'])
def work():
    print(request.form)
    id = request.form['id']
    print(id)
    qry = "SELECT * FROM `work` WHERE `contractor_id`=%s"
    val = (id)
    res = androidselectall(qry,val)
    return jsonify(res)

@app.route('/skills',methods=['post'])
def skills():
    id = request.form['id']
    print(id)
    qry = "SELECT * FROM `features` WHERE `contractor_id`=%s"
    val = (id)
    res = androidselectall(qry,val)
    print(res)
    return jsonify(res)

@app.route('/request_contractor',methods=['post'])
def request_contractor():
    print(request.form)
    id = request.form['id']
    cid = request.form['cid']
    work = request.form['work']
    qry = "INSERT INTO `request` VALUES(NULL,%s,%s,%s,CURDATE(),'pending')"
    val = (id,cid,work)
    iud(qry,val)
    return jsonify({'task':'success'})

@app.route('/sharelocation',methods=['post'])
def sharelocation():
    lati=request.form['lati']
    longi=request.form['longi']
    lid=request.form['id']
    q="select * from location where user_id=%s"
    res=selectone(q,lid)
    if res is None:
        q="insert into location values(null,%s,%s,%s)"
        v=lid,lati,longi
        iud(q,v)
    else:
        q="update location set lattitude=%s,longitude=%s where user_id=%s"
        v=lati,longi,lid
        iud(q,v)



    return jsonify({'task':'success'})

# view contractors for search function in buy products
@app.route('/view_contractors',methods=['post'])
def view_contractors():
    qry = "SELECT * FROM `register`"
    res = androidselectallnew(qry)
    return jsonify(res)


@app.route('/view_request_status',methods=['post'])
def view_request_status():
    print(request.form)
    id = request.form['id']

    qry = "SELECT * FROM `request` WHERE user_id=%s"
    val = (id)
    res = androidselectall(qry,val)
    print(res)
    return jsonify(res)

@app.route('/view_productss',methods=['post'])
def view_productss():
    print(request.form)
    id = request.form['id']
    qry = "SELECT `product`.`product_name`,`orderdproduct`.`quantity`,`orderdproduct`.`price`,`product_img`.* FROM `orderdproduct` JOIN `product` ON `product`.`product_id`=`orderdproduct`.`product_id` JOIN `product_img` ON `product`.`product_id`=`product_img`.pid WHERE `orderdproduct`.`order_id`=%s"
    val = (id)
    res = androidselectall(qry,val)
    print(res)
    return jsonify(res)

@app.route('/view_products',methods=['post'])
def view_products():
    print(request.form)
    id = request.form['id']
    qry = "SELECT `product`.*,`product_img`.* FROM `product` JOIN `product_img` ON `product`.`product_id`=`product_img`.pid WHERE product.`contractor_id`=%s AND product.`quantity`>=1"
    val = (id)
    res = androidselectall(qry,val)
    if len(res)>0:
        return jsonify(data=res,status="ok")
    else:
        return jsonify(status="")

@app.route('/add_to_cart',methods=['post'])
def add_to_cart():
    qty = request.form['quantity']
    id = request.form['id']
    qry = "INSERT INTO `order` VALUES(NULL,%s,%s,CURDATE())"
    val = (id,qty)
    iud(qry,val)
    return jsonify({'task':'success'})

@app.route('/cart_item_view',methods=['post'])
def cart_item_view():
    id = request.form['id']
    qry = "SELECT * FROM `order` WHERE `user_id`=%s"
    val=(id)
    res = androidselectall(qry,val)
    return jsonify(res)

# @app.route('/purchase_product')
# def purchase_product():
#     amount = request.form['amount']
#     accno = request.form['accno']
#     ifsc = request.form['ifsc']
#     pin = request.form['pin']

@app.route('/view_ordered_products',methods=['post'])
def view_ordered_products():
    id = request.form['id']
    print(id)
    qry = "SELECT`order`.* FROM `order` WHERE `order`.`user_id`=%s"
    val = (id)
    res = androidselectall(qry,val)
    print(res)
    return jsonify(res)

# @app.route('/chat')
# def chat():

@app.route('/view_vaccancy',methods=['post'])
def view_vaccancy():
    qry = "SELECT * FROM `vaccancy`"
    res = androidselectallnew(qry)
    return jsonify(res)

@app.route('/add_complaint',methods=['post'])
def add_complaint():
    id = request.form['id']
    cmplnt = request.form['complaint']
    qry = "INSERT INTO `complaint` VALUES(NULL,%s,CURDATE(),%s,'pending')"
    val = (cmplnt,id)
    iud(qry,val)
    return jsonify({'task':'valid'})

@app.route('/send_feedback',methods=['post'])
def send_feedback():
    id = request.form['id']
    feedback = request.form['feedback']
    qry = "INSERT INTO `feedback` VALUES(NULL,%s,%s,CURDATE(),'customer')"
    val = (feedback,id)
    iud(qry,val)
    return jsonify({'task':'valid'})

@app.route('/view_reply',methods=['post'])
def view_reply():
    print(request.form)
    id = request.form['id']
    qry = "SELECT * FROM `complaint` WHERE `user_id`=%s"
    val = (id)
    res = androidselectall(qry,val)
    print(res)
    return jsonify(res)


@app.route('/view_application_status',methods=['post'])
def view_application_status():
    id = request.form['id']
    qry = "SELECT `job application`.* ,`vaccancy`.`details`,`vaccancy`.`job` FROM `job application` JOIN `vaccancy` ON `job application`.`vaccancy_id`=`vaccancy`.`vaccancy_id` WHERE `job application`.`user_id`=%s"
    val = (id)
    res = androidselectall(qry,val)
    return  jsonify(res)




@app.route('/applyjob',methods=['post'])
def applyjob():
    id = request.form['vid']
    lid = request.form['lid']
    qry = "SELECT * FROM `job application` WHERE user_id=%s AND vaccancy_id=%s"
    val=(lid,id)
    res=selectone(qry,val)
    if res is  None:
        qry="INSERT INTO `job application` VALUES(NULL,%s,%s,CURDATE(),'pending')"
        val=(id,lid)
        iud(qry,val)
        return jsonify({'task':'valid'})
    else:
        return jsonify({'task': 'invalid'})




@app.route('/inserorder',methods=['post'])
def inserorder():
    print(request.form)
    id = request.form['uid']
    oid = request.form['oid']
    pid=request.form['pid']
    qty = request.form['qty']
    prc=request.form['prc']
    tt=int(prc)*int(qty)
    qry = "INSERT INTO `orderdproduct` VALUES(NULL,%s,%s,%s,%s)"
    val = (pid,qty,str(tt),oid)
    iud(qry,val)
    q="update product set quantity=quantity-%s where product_id=%s"
    v=qty,pid
    iud(q,v)
    return jsonify({'task':'ok'})
@app.route('/finish',methods=['post'])
def finish():
    print(request.form)
    oid = request.form['oid']
    qry = "SELECT SUM(`price`) FROM `orderdproduct` WHERE `order_id`=%s"
    val = (oid)
    res=selectone(qry,val)
    tot=res[0]
    q="update `order` set total=%s,stauts='ordered' where order_id=%s"
    v=tot,oid
    iud(q,v)
    return jsonify({'task':'ok'})


@app.route('/getoid',methods=['post'])
def getoid():
    id=request.form['id']
    q="insert into `order` values(null,%s,'0',curdate(),'pending')"
    id=iud(q,id)
    return jsonify({'task':str(id)})


@app.route('/in_message2',methods=['post'])
def in_message():
    print(request.form)
    fromid = request.form['fid']
    print("fromid",fromid)

    toid = request.form['toid']
    print("toid",toid)

    message=request.form['msg']
    print("msg",message)
    qry = "INSERT INTO `chat` VALUES(NULL,%s,%s,%s,CURDATE())"
    value = (fromid, toid, message)
    print("pppppppppppppppppp")
    print(value)
    iud(qry, value)
    return jsonify(status='send')

@app.route('/view_message2',methods=['post'])
def view_message2():
    print("wwwwwwwwwwwwwwww")
    print(request.form)
    fromid=request.form['fid']
    print(fromid)
    toid=request.form['toid']
    print(toid)
    lmid = request.form['lastmsgid']
    print("msgggggggggggggggggggggg"+lmid)
    sen_res = []
    # qry="SELECT * FROM chat WHERE (fromid=%s AND toid=%s) OR (fromid=%s AND toid=%s) ORDER BY DATE ASC"
    qry="SELECT `fromid`,`message`,`date`,`chatid` FROM `chat` WHERE `chatid`>%s AND ((`toid`=%s AND  `fromid`=%s) OR (`toid`=%s AND `fromid`=%s)  )  ORDER BY chatid ASC"

    val=(str(lmid),str(toid),str(fromid),str(fromid),str(toid))
    print("fffffffffffff",val)
    res = androidselectall(qry,val)
    print("resullllllllllll")
    print(res)
    if res is not None:
        return jsonify(status='ok', res1=res)
    else:
        return jsonify(status='not found')


@app.route('/pay',methods=['post'])
def pay():
    try:
        print (request.form)
        oid=request.form['oid']
        uid=request.form['uid']
        acc=request.form['acc']
        keys=request.form['key']
        ifsc=request.form['ifsc']
        q="select total from `order` where order_id=%s"
        res=selectone(q,oid)
        tt=res[0]
        q="SELECT * FROM `bank` WHERE `accnumber`=%s AND `uid`=%s AND `key`=%s AND `ifsccode`=%s"
        vv=acc,uid,keys,ifsc
        ress=selectone(q,vv)
        if ress is None:
            return jsonify({'task':'invalid'})
        else:
            amt=ress[5]
            if int(amt)>int(tt):
                q="update bank set amount=amount-%s where bid=%s"
                v=(tt,ress[0])
                iud(q,v)
                q="UPDATE `order` SET stauts='paid' WHERE order_id=%s"
                iud(q,oid)
                return jsonify({'task': 'success'})
            else:
                return jsonify({'task': 'noamount'})
    except Exception as e:
        print (e)
        return jsonify({'task': 'invalid'})






app.run(host='0.0.0.0',port=5000,debug=True)
