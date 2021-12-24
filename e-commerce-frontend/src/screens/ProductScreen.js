import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { detailsProduct, saveProductReview } from '../actions/productActions';
import Rating from '../components/Rating';
import { PRODUCT_REVIEW_SAVE_RESET } from '../constants/productConstants';
import { addToCart } from '../actions/cartActions';
import Swal from 'sweetalert2';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAngleLeft } from '@fortawesome/free-solid-svg-icons';
import Lottie from 'react-lottie';
import animationLoadingData from '../lotties/loading-dots-blue.json';
import { ShowAddProductToCartSuccess, ShowAddProductReviewSuccess } from '../components/Alert'

function ProductScreen(props) {
  const [qty, setQty] = useState(1);
  const [rating, setRating] = useState(1);
  const [comment, setComment] = useState('');
  const userSignin = useSelector((state) => state.userSignin);
  const { userInfo } = userSignin;
  const productDetails = useSelector((state) => state.productDetails);
  const { product, loading, error } = productDetails;
  const productReviewSave = useSelector((state) => state.productReviewSave);
  const { success: productSaveSuccess } = productReviewSave;
  const dispatch = useDispatch();

  useEffect(() => {
    if (productSaveSuccess) {
      ShowAddProductReviewSuccess();
      setRating(0);
      setComment('');
      dispatch({ type: PRODUCT_REVIEW_SAVE_RESET });
    }
    dispatch(detailsProduct(props.match.params.id));
    return () => {
      //
    };
  }, [productSaveSuccess]);
  const submitHandler = (e) => {
    e.preventDefault();
    // dispatch actions
    dispatch(
      saveProductReview({
        productId: props.match.params.id,
        userName: userInfo.name,
        rating: rating,
        comment: comment,
      })
    );
  };
  const handleAddToCart = () => {
    if(!userInfo) return props.history.push('/signin?redirect=')
    if (props.match.params.id) {
      dispatch(addToCart(props.match.params.id, qty, "addQty"));
      ShowAddProductToCartSuccess();
    }
  };

  const defaultLoadingOptions = {
    loop: true,
    autoplay: true,
    animationData: animationLoadingData,
    rendererSettings: {
      preserveAspectRatio: "xMidYMid slice"
    }
  };

  return (
    <div>
      <div className="back-to-result">
        <Link to={"/"} className="back-button">
          <FontAwesomeIcon className="icon" icon={faAngleLeft} size="2x" />
          <span>Back to result</span>
        </Link>
      </div>
      {loading ? (
         <Lottie 
          options={defaultLoadingOptions}
          height={200}
          width={200}
          style={{
            marginBottom: '50%'
          }}
        />
      ) : error ? (
        <div>{error} </div>
      ) : (
        <>
          <div className="details">
            <div className="details-left">
              <img 
                src={product.image} 
                alt="product"
                />
            </div>
            <div className="details-right">
              <div className="details-info">
                <ul>
                  <li>
                    <h2>{product.name}</h2>
                  </li>
                  <li>
                    <a href="#reviews">
                      <Rating
                        value={product.rating}
                        text={product.productReviewList.length + ' reviews'}
                      />
                    </a>
                  </li>
                  <li>
                    Price: <b>Rp{product.price}</b>
                  </li>
                </ul>
              </div>
              <div className="details-action">
                <ul>
                  <li>Price: {product.price}</li>
                  <li>
                    Status:{' '}
                    {product.quantityInStock > 0 ? 'In Stock' : 'Unavailable.'}
                  </li>
                  <li>
                  Qty:{' '}
                    <select
                    className="qty-select"
                      value={qty}
                      onChange={(e) => {
                        setQty(e.target.value);
                      }}
                    >
                      {[...Array(product.quantityInStock).keys()].map((x) => (
                        <option key={x + 1} value={x + 1}>
                          {x + 1}
                        </option>
                      ))}
                    </select>
                    <span>
                    {product.quantityInStock+' '}piece left   
                    </span>
                  </li>
                  <li>
                    {product.quantityInStock > 0 && (
                      <button
                        onClick={handleAddToCart}
                        className="button primary"
                      >
                        Add to Cart
                      </button>
                    )}
                  </li>
                </ul>
              </div>
              <div className="details-desc">
                  <div>Description of Product :</div>
                  <div>{product.description}</div>
              </div>
            </div>
            
          </div>
          <div className="content-margined">
            <h2>Reviews</h2>
            {!product.productReviewList.length && <div>There is no review</div>}
            <ul className="review" id="reviews">
              {product.productReviewList.map((review) => (
                <li key={review.id} className="">
                  <div className="review-user">
                   {
                      review.image!=null?
                        <img
                          src={review.image}
                          alt="review-img-user"
                        />
                      :
                        <div>
                          {review.userName.substring(0, 1)}
                        </div>
                    }
                  </div>
                  <div className="review-content">
                    <div>{review.userName}</div>
                    <div>
                      <Rating value={review.rating}></Rating>
                    </div>
                    <div className="comment">
                      <div>{review.comment}</div>
                      <div className="date">{review.createdDate.substring(0, 10)}</div>
                    </div>
                  </div>
                </li>
              ))}
            </ul>
            <div className="write">
            <h3>Write a customer review</h3>
                {userInfo ? (
                  <form onSubmit={submitHandler}>
                    <ul className="form-container">
                      <li>
                        <label htmlFor="rating">Rating</label>
                        <select
                          name="rating"
                          id="rating"
                          value={rating}
                          onChange={(e) => setRating(e.target.value)}
                        >
                          <option value="1">1- Poor</option>
                          <option value="2">2- Fair</option>
                          <option value="3">3- Good</option>
                          <option value="4">4- Very Good</option>
                          <option value="5">5- Excelent</option>
                        </select>
                      </li>
                      <li>
                        <label htmlFor="comment">Comment</label>
                        <textarea
                          name="comment"
                          value={comment}
                          onChange={(e) => setComment(e.target.value)}
                        ></textarea>
                      </li>
                      <li>
                        <button type="submit" className="button primary">
                          Submit
                        </button>
                      </li>
                    </ul>
                  </form>
                ) : (
                  <div>
                    Please <Link to={"/signin"}>Sign-in</Link> to write a review.
                  </div>
                )}
            </div>
            
          </div>
        </>
      )}
    </div>
  );
}
export default ProductScreen;
