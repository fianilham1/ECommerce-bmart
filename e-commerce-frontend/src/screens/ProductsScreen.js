import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { Redirect } from 'react-router-dom';
import {
  saveProduct,
  listProducts,
  deleteProdcut,
} from '../actions/productActions';
import Swal from 'sweetalert2';

function ProductsScreen() {
  const [modalVisible, setModalVisible] = useState(false);
  const [id, setId] = useState('');
  const [name, setName] = useState('');
  const [price, setPrice] = useState('');
  const [image, setImage] = useState('');
  const [brand, setBrand] = useState('');
  const [expDate, setExpDate] = useState('');
  const [code, setCode] = useState('');
  const [productSeries, setProductSeries] = useState('');
  const [netWeight, setNetWeight] = useState('');
  const [size, setSize] = useState('');
  const [categoryId, setCategoryId] = useState('');
  const [category, setCategory] = useState('');
  const [quantityInStock, setQuantityInStock] = useState('');
  const [description, setDescription] = useState('');
  // const [uploading, setUploading] = useState(false);
  const productList = useSelector((state) => state.productList);
  const { products } = productList;

  const numRegex = /^[0-9\b]+$/;

  const productSave = useSelector((state) => state.productSave);
  const {
    loading: loadingSave,
    success: successSave,
    error: errorSave,
  } = productSave;

  const productDelete = useSelector((state) => state.productDelete);
  const {
    // loading: loadingDelete,
    success: successDelete,
    // error: errorDelete,
  } = productDelete;
  const dispatch = useDispatch();

  const productCategory = useSelector(state => state.productCategory);
  const { productCategoryList } = productCategory;

  const userSignin = useSelector((state) => state.userSignin);
  const { userInfo } = userSignin;

  useEffect(() => {
    if (successSave) {
      setModalVisible(false);
    }
    dispatch(listProducts());
    return () => {
      //
    };
  }, [successSave, successDelete]);

  const openModal = (product) => {
    console.log("category clicked :",category)
    setModalVisible(true);
    setId(product?.id ? product.id : '');
    setName(product?.name ? product.name : '');
    setPrice(product?.price ? product?.price : '');
    setDescription(product?.description ? product.description : '');
    setImage(product?.image ? product.image : '');
    setBrand(product?.brand ? product.brand : '');
    setExpDate(product?.expDate ? product.expDate : '')
    setCode(product?.code ? product.code : '')
    setProductSeries(product?.productSeries ? product.productSeries : '')
    setNetWeight(product?.netWeight ? product.netWeight : '')
    setSize(product?.size ? product.size : '')
    setCategory(product?.productCategory?.name ? product.productCategory.name : '');
    setCategoryId(product?.productCategory?.id ? product.productCategory.id : productCategoryList[0].id);
    setQuantityInStock(product?.quantityInStock ? product.quantityInStock : '');
  };
  const submitHandler = (e) => {
    e.preventDefault();
    dispatch(
      saveProduct({
        id: id,
        name,
        price,
        image,
        brand,
        expDate,
        code,
        productSeries,
        netWeight,
        size,
        productCategory:{
          id:categoryId
        },
        quantityInStock,
        description,
      })
    );
  };
  const deleteHandler = (id) => {
    Swal.fire({
      title: 'Are you sure to delete?',
      text: "Can't be delete if the product has been added to customer's cart!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete!'
    }).then((result) => {
      if (result.isConfirmed) {
        // delete feature currently is non-active --------------------- xxxxxxxxxxx
        // dispatch(deleteProdcut(id));
      }
    })
  };
  // const uploadFileHandler = (e) => {
  //   const file = e.target.files[0];
  //   const bodyFormData = new FormData();
  //   bodyFormData.append('image', file);
  //   setUploading(true);
  //   axios
  //     .post('/api/uploads', bodyFormData, {
  //       headers: {
  //         'Content-Type': 'multipart/form-data',
  //       },
  //     })
  //     .then((response) => {
  //       setImage(response.data);
  //       setUploading(false);
  //     })
  //     .catch((err) => {
  //       console.log(err);
  //       setUploading(false);
  //     });
  // };

  if(!userInfo) return <Redirect to="/signin?redirect=" />

  return (
    <div className="content content-margined">
      <div className="product-header">
        <h3>Products</h3>
        <button className="button primary" onClick={() => openModal({})}>
          Create Product
        </button>
      </div>
      {modalVisible && (
        <div className="form">
          <form onSubmit={submitHandler}>
            <ul className="form-container">
              <li>
                <h2>{id ? 'Update Product' : 'Create Product'}</h2>
              </li>
              <li>
                {loadingSave && <div>Loading...</div>}
                {errorSave && <div>{errorSave}</div>}
              </li>

              <li>
                <label htmlFor="name">Name</label>
                <input
                  required
                  type="text"
                  name="name"
                  value={name}
                  id="name"
                  onChange={(e) => setName(e.target.value)}
                ></input>
              </li>
              <li>
                <label htmlFor="price">Price (Rp)</label>
                <input
                  required
                  type="text"
                  name="price"
                  value={price}
                  id="price"
                  onChange={(e) => {
                    if (e.target.value==='' || numRegex.test(e.target.value)) {
                      setPrice(e.target.value)
                    }
                    }}
                ></input>
              </li>
              <li>
                <label htmlFor="image">Image</label>
                <input
                  required
                  type="text"
                  name="image"
                  value={image}
                  id="image"
                  onChange={(e) => setImage(e.target.value)}
                ></input>
                {/* <input type="file" onChange={uploadFileHandler}></input>
                {uploading && <div>Uploading...</div>} */}
              </li>
              <li>
                <label htmlFor="brand">Brand</label>
                <input
                  required
                  type="text"
                  name="brand"
                  value={brand}
                  id="brand"
                  onChange={(e) => setBrand(e.target.value)}
                ></input>
              </li>
              <li>
                <label htmlFor="countInStock">CountInStock</label>
                <input
                  required
                  type="text"
                  name="countInStock"
                  value={quantityInStock}
                  id="countInStock"
                  onChange={(e) => {
                    if (e.target.value==='' || numRegex.test(e.target.value)) {
                      setQuantityInStock(e.target.value)
                    }
                  }}
                ></input>
              </li>
              <li>
                <label htmlFor="expDate">Exp Date</label>
                <input
                  type="text"
                  name="expDate"
                  value={expDate}
                  id="expDate"
                  onChange={(e) => setExpDate(e.target.value)}
                ></input>
              </li>
              <li>
                <label htmlFor="code">Code</label>
                <input
                  required
                  type="text"
                  name="code"
                  value={code}
                  id="code"
                  onChange={(e) => setCode(e.target.value)}
                ></input>
              </li>
              <li>
                <label htmlFor="productSeries">Product Series</label>
                <input
                  type="text"
                  name="productSeries"
                  value={productSeries}
                  id="productSeries"
                  onChange={(e) => setProductSeries(e.target.value)}
                ></input>
              </li>
              <li>
                <label htmlFor="netWeight">Net Weight</label>
                <input
                  required
                  type="text"
                  name="netWeight"
                  value={netWeight}
                  id="netWeight"
                  onChange={(e) => {
                    if (e.target.value==='' || numRegex.test(e.target.value)) {
                      setNetWeight(e.target.value)
                    }
                  }}
                ></input>
              </li>
              <li>
                <label htmlFor="size">Size Info</label>
                <input
                  type="text"
                  name="size"
                  value={size}
                  id="size"
                  onChange={(e) => setSize(e.target.value)}
                ></input>
              </li>
              <li>
                <label htmlFor="name">Category</label>
                <select value={categoryId} className="category-edit" onChange={(e) => setCategoryId(e.target.value)}>
                  {
                    productCategoryList.map((category)=>(
                      <option key={category.id} value={category.id}>{category.name}</option>
                    ))
                  }
                </select>
              </li>
              <li>
                <label htmlFor="description">Description</label>
                <textarea
                  name="description"
                  value={description}
                  id="description"
                  onChange={(e) => setDescription(e.target.value)}
                ></textarea>
              </li>
              <li>
                <button type="submit" className="button primary">
                  {id ? 'Update' : 'Create'}
                </button>
              </li>
              <li>
                <button
                  type="button"
                  onClick={() => setModalVisible(false)}
                  className="button secondary text-center"
                >
                  Back
                </button>
              </li>
            </ul>
          </form>
        </div>
      )}

      <div className="product-list">
        <table className="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Price</th>
              <th>Category</th>
              <th>Brand</th>
              <th>Expired</th>
              <th>Code</th>
              <th>Product Series</th>
              <th>Net Weight (gr)</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {products.map((product) => (
              <tr key={product.id}>
                <td>{product.id}</td>
                <td>{product.name}</td>
                <td>Rp{product.price}</td>
                <td>{product.productCategory.name}</td>
                <td>{product.brand}</td>
                <td>{product.expDate}</td>
                <td>{product.code}</td>
                <td>{product.productSeries}</td>
                <td>{product.netWeight}</td>
                <td>
                  <button className="button edit" onClick={() => openModal(product)}>
                    Edit
                  </button>{' '}
                  <button
                    className="button delete"
                    onClick={() => deleteHandler(product.id)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
export default ProductsScreen;
