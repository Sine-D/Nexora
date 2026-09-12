import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { describe, it, expect, vi } from 'vitest';
import TicketForm from './TicketForm';

vi.mock('./api', () => ({
  createTicket: vi.fn(() => Promise.resolve()),
  updateTicket: vi.fn(() => Promise.resolve()),
}));

describe('TicketForm Component', () => {
  it('renders form input fields correctly', () => {
    render(<TicketForm onSaved={() => {}} onCancel={() => {}} />);
    
    expect(screen.getByLabelText(/Title/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Description/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Status/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Assignee/i)).toBeInTheDocument();
  });

  it('populates form fields when editing an existing ticket', () => {
    const existingTicket = {
      id: 1,
      title: 'Fix Login Exception',
      description: 'Handled in AuthService',
      status: 'OPEN',
      assignee: 'Alex',
    };

    render(<TicketForm ticket={existingTicket} onSaved={() => {}} onCancel={() => {}} />);

    expect(screen.getByDisplayValue('Fix Login Exception')).toBeInTheDocument();
    expect(screen.getByDisplayValue('Handled in AuthService')).toBeInTheDocument();
    expect(screen.getByText('Update')).toBeInTheDocument();
  });

  it('calls onCancel when Cancel button is clicked', () => {
    const onCancelMock = vi.fn();
    render(<TicketForm onSaved={() => {}} onCancel={onCancelMock} />);

    fireEvent.click(screen.getByText('Cancel'));
    expect(onCancelMock).toHaveBeenCalledTimes(1);
  });
});
