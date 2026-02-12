import React, { useState, useEffect, useCallback } from 'react';
import './OrderList.css';

function OrderList() {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [newProduct, setNewProduct] = useState('');
    const [newQuantity, setNewQuantity] = useState(1);
    const [selectedOrder, setSelectedOrder] = useState(null);

    const fetchOrders = useCallback(async () => {
        setLoading(true);
        try {
            const response = await fetch('/orders');
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const data = await response.json();
            setOrders(data);
            setLoading(false);
        } catch (error) {
            setError(error);
            setLoading(false);
        }
    }, []);

    useEffect(() => {
        fetchOrders();
    }, [fetchOrders]);

    const handleCreate = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch('/orders', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ product: newProduct, quantity: newQuantity }),
            });
            if (!response.ok) {
                throw new Error('Failed to create order');
            }
            setNewProduct('');
            setNewQuantity(1);
            fetchOrders(); // Refresh the list
        } catch (error) {
            setError(error);
        }
    };

    const handleDelete = async (id) => {
        try {
            const response = await fetch(`/orders/${id}`, {
                method: 'DELETE',
            });
            if (!response.ok) {
                throw new Error('Failed to delete order');
            }
            if (selectedOrder && selectedOrder.id === id) {
                setSelectedOrder(null);
            }
            fetchOrders(); // Refresh the list
        } catch (error) {
            setError(error);
        }
    };

    const handleView = async (id) => {
        try {
            const response = await fetch(`/orders/${id}`);
            if (!response.ok) {
                throw new Error('Failed to fetch order details');
            }
            const data = await response.json();
            setSelectedOrder(data);
        } catch (error) {
            setError(error);
        }
    };

    if (loading) {
        return <div className="loading">Loading...</div>;
    }

    if (error) {
        return <div className="error">Error: {error.message}</div>;
    }

    return (
        <div className="order-container">
            <h1>Order Management</h1>

            <h2>Create New Order</h2>
            <form className="order-form" onSubmit={handleCreate}>
                <input
                    type="text"
                    value={newProduct}
                    onChange={(e) => setNewProduct(e.target.value)}
                    placeholder="Product Name"
                    required
                />
                <input
                    type="number"
                    value={newQuantity}
                    onChange={(e) => setNewQuantity(parseInt(e.target.value, 10))}
                    placeholder="Quantity"
                    min="1"
                    required
                />
                <button type="submit" className="btn-create">Create Order</button>
            </form>

            <h2>Order List</h2>
            <ul className="order-list">
                {orders.map(order => (
                    <li key={order.id} className="order-item">
                        <div className="order-details">
                            <strong>Product:</strong> {order.product} <br/>
                            <strong>Quantity:</strong> {order.quantity}
                        </div>
                        <div className="order-actions">
                            <button onClick={() => handleView(order.id)} className="btn-view">
                                View
                            </button>
                            <button onClick={() => handleDelete(order.id)} className="btn-delete">
                                Delete
                            </button>
                        </div>
                    </li>
                ))}
            </ul>

            {selectedOrder && (
                <div className="order-modal-overlay">
                    <div className="order-modal">
                        <h2>Order Details</h2>
                        <div className="modal-content">
                            <p><strong>ID:</strong> {selectedOrder.id}</p>
                            <p><strong>Product:</strong> {selectedOrder.product}</p>
                            <p><strong>Quantity:</strong> {selectedOrder.quantity}</p>
                            <p><strong>Created At:</strong> {selectedOrder.createdAt || 'N/A'}</p>
                        </div>
                        <button onClick={() => setSelectedOrder(null)} className="btn-close">Close</button>
                    </div>
                </div>
            )}
        </div>
    );
}

export default OrderList;
