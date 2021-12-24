import React, { useState, useEffect } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { listProducts } from '../actions/productActions';
import Rating from '../components/Rating';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSearch } from '@fortawesome/free-solid-svg-icons';
import Lottie from 'react-lottie';
import animationLoadingData from '../lotties/loading-dots-blue.json';

function useQuery() {
  const { search } = useLocation();

  return React.useMemo(() => new URLSearchParams(search), [search]);
}

function HomeScreen(props) {
  let query = useQuery();
  const searchKeywordQuery = query.get("keyword");

  const [searchKeyword, setSearchKeyword] = useState('');
  const [sortOrder, setSortOrder] = useState('');
  const category = props.match.params.id ? props.match.params.id : '';
  const productList = useSelector((state) => state.productList);
  const { products, loading, error } = productList;
  const dispatch = useDispatch();

  useEffect(() => {
    console.log("category : ",category)
    if(searchKeywordQuery){
      return setSearchKeyword(searchKeywordQuery);
    }
    dispatch(listProducts(category));
    return () => {
      //
    };
  }, [category]);

  const submitHandler = (e) => {
    e.preventDefault();
    dispatch(listProducts(category, searchKeyword, sortOrder));
    const keywordParams = searchKeyword ? 'keyword='+searchKeyword : '';
    if(keywordParams) return props.history.push('/search?'+keywordParams);
    props.history.push('/');
  };
  const sortHandler = (e) => {
    setSortOrder(e.target.value);
    dispatch(listProducts(category, searchKeyword, e.target.value));
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
    <>
      {category && <div className="category-header">{category.charAt(0).toUpperCase() + category.slice(1)}</div>}
      <ul className="filter">
        <li>
          <form onSubmit={submitHandler}>
            <input
              name="searchKeyword"
              value={searchKeyword}
              onChange={(e) => setSearchKeyword(e.target.value)}
            />
            <button type="submit" className="searchIcon" >
              <FontAwesomeIcon  icon={faSearch} size="lg" />
            </button>
          </form>
        </li>
      </ul>
      <div className="sort">
          <span>Sort By{' '}</span>
          <select name="sortOrder" onChange={sortHandler}>
            <option value="">Newest</option>
            <option value="price/asc">Cheapest</option>
            <option value="price/desc">Most Expensive</option>
            <option value="rating/desc">Best Seller</option>
          </select>
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
        <div>{error}</div>
      ) : (
        <ul className="products">
          {products.map((product) => (
            <li key={product.id}>
              <div className="product">
                <Link to={'/product/' + product.id}>
                  <img
                    className="product-image"
                    src={product.image}
                    alt="product"
                  />
                </Link>
                <div className="product-name">
                  <Link to={'/product/' + product.id}>{product.name}</Link>
                </div>
                <div className="product-brand">{product.brand}</div>
                <div className="product-price">Rp{product.price}</div>
                <div className="product-rating">
                  <Rating
                    value={product.rating}
                    text={product.productReviewList.length + ' reviews'}
                  />
                </div>
              </div>
            </li>
          ))}
        </ul>
      )}
    </>
  );
}
export default HomeScreen;
